package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceCompletionImpl;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiFileSystemItemProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.*;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

/**
 * Represents a reference to some directory (or specific {@link PsiFile} file) introduced via a uses clause or
 * super module (Impl X for Y).
 * <p>
 * This is distinguished from an {@link ResReferenceExp} that reference modules in either:
 * <ul><li>a facility decl</li>
 * <li>a type (as a qualifier, etc)</li>
 * </ul></p>
 */
public class ResModuleReference extends FileReference {

    ResModuleReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @Override
    public PsiFileSystemItem resolve() {
        final PsiFileSystemItem result = super.resolve();
        if (result instanceof ResFile) {
            return result;
        }
        return null;
    }

    //TREAT the 'from' clause into myReferences in the completion set (at the end) then you'll have a convenient,
    //automatically searcheable context.

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive, @NotNull PsiFile file) {
        //there's got to be a real way of searching for extensioned filetypes... geez
        String referenceText = getText();

        Set<ResolveResult> result = ContainerUtil.newLinkedHashSet();
        Set<ResolveResult> innerResult = ContainerUtil.newLinkedHashSet();
        Collection<PsiFileSystemItem> ctxs = getContexts();

        for (PsiFileSystemItem context : ctxs) {
            innerResolveInContext(referenceText, context, innerResult, caseSensitive);
            for (ResolveResult resolveResult : innerResult) {
                PsiElement element = resolveResult.getElement();
                if (element instanceof ResFile) {
                    result.add(resolveResult);
                }
            }
        }
        return result.isEmpty() ? ResolveResult.EMPTY_ARRAY : result.toArray(new ResolveResult[result.size()]);
    }

    @Override
    protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof ResFile) {
            return RESOLVECompletionUtil.createResolveFileLookupElement((ResFile) candidate);
        }
        return super.createLookupItem(candidate);
    }

    /*@Override
    public boolean isReferenceTo(PsiElement element) {
        if (super.isReferenceTo(element)) {
            return true;
        }

        if (element instanceof PsiDirectoryContainer) {
            for (PsiDirectory directory : ((PsiDirectoryContainer) element).getDirectories()) {
                if (super.isReferenceTo(directory)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element, boolean absolute) throws IncorrectOperationException {
        if (!absolute) {
            FileReference firstReference = ArrayUtil.getFirstElement(getFileReferenceSet().getAllReferences());
            if (firstReference != null) {
                Collection<PsiFileSystemItem> contexts = getFileReferenceSet().getDefaultContexts();
                for (ResolveResult resolveResult : firstReference.multiResolve(false)) {
                    PsiElement resolveResultElement = resolveResult.getElement();
                    if (resolveResultElement instanceof PsiFileSystemItem) {
                        PsiFileSystemItem parentDirectory = ((PsiFileSystemItem) resolveResultElement).getParent();
                        if (parentDirectory != null && contexts.contains(parentDirectory)) {
                            return getElement();
                        }
                    }
                }
            }
        }
        return super.bindToElement(element, absolute);
    }*/


    /**
     * Represents a collection of file references along with some number of underlying default file searching
     * contexts (folders, directories, etc).
     * <p>
     * This is a companion class
     * to {@link ResModuleReference}; so don't be fooled by the seeming lack of connection between the two. The
     * main thing I do here is implement {@link #getDefaultContexts()}, which ultimately influences which folders
     * (contexts) are searched through by the {@link ResModuleReference#innerResolve(boolean, PsiFile)} method.
     *
     * @author dtwelch
     */
    public static class ResModuleReferenceSet extends FileReferenceSet {

        /**
         * Apparently overriding {@link ResModuleReference#createLookupItem(com.intellij.psi.PsiElement)} isn't
         * enough to keep things like {@link PsiDirectory} and other {@link PsiFile}s from getting included in completions
         * carried out by {@link FileReferenceCompletionImpl#getFileReferenceCompletionVariants(FileReference)} happening
         * in the background.
         * <p>
         * Turns out you need to override {@link #getReferenceCompletionFilter()}, returning the {@link Condition}
         * instance declared below. How exactly clients (e.g., me) were supposed to figure this out without any sort of
         * documentation whatsoever is beyond me.
         */
        private static final Condition<PsiFileSystemItem> RES_FILE_FILTER = e -> e instanceof ResFile;

        public ResModuleReferenceSet(@NotNull ResModuleIdentifier moduleIdentifier) {
            super(moduleIdentifier.getText(), moduleIdentifier,
                    moduleIdentifier.getModuleIdentiferTextRange().getStartOffset(), null, true);
        }

        //OK, here we need to only add the current projects context along with the 'usr/local/resolve/src (STD)' sources.
        @NotNull
        @Override
        public Collection<PsiFileSystemItem> computeDefaultContexts() {
            PsiFile file = getContainingFile();
            if (file == null || !file.isValid() || isAbsolutePathReference()) {
                return Collections.emptyList();
            }

            final PsiManager psiManager = file.getManager();
            Module module = ModuleUtilCore.findModuleForPsiElement(file);
            Project project = file.getProject();

            LinkedHashSet<VirtualFile> sourceRoots = newLinkedHashSet();

            PsiElement e = getElement();
            //handle the 'from' clause..

            ResModuleLibraryIdentifier desiredLib = null;
            //TODO: technically parent can also now be a facility
            if (e.getParent() instanceof ResModuleIdentifierSpec &&
                    ((ResModuleIdentifierSpec) e.getParent()).getFromLibraryIdentifier() != null) {
                desiredLib = ((ResModuleIdentifierSpec) e.getParent()).getModuleLibraryIdentifier();
            }
            else if (e.getParent() instanceof ResFacilityDecl &&
                    PsiTreeUtil.getNextSiblingOfType(e, ResModuleLibraryIdentifier.class) != null) {
                desiredLib = PsiTreeUtil.getNextSiblingOfType(e, ResModuleLibraryIdentifier.class);
            }
            else if (e.getParent() instanceof ResExtensionPairing &&
                    PsiTreeUtil.getNextSiblingOfType(e, ResModuleLibraryIdentifier.class) != null) {
                desiredLib = PsiTreeUtil.getNextSiblingOfType(e, ResModuleLibraryIdentifier.class);
            }

            if (desiredLib != null) {
                PsiElement ele = desiredLib.resolve();
                if (ele != null && ele instanceof PsiDirectory) {
                    addContexts(sourceRoots, ((PsiDirectory) ele).getVirtualFile());
                    //sourceRoots.add(((PsiDirectory) ele).getVirtualFile());
                }
            }
            else {
                VirtualFile rootSdkDir = RESOLVESdkUtil.getSdkSrcDir(project, module);
                if (rootSdkDir != null) addContexts(sourceRoots, rootSdkDir);
                //now do the curr proj.
                if (module != null) addContexts(sourceRoots, project.getBaseDir());
            }
            return ContainerUtil.mapNotNull(sourceRoots, psiManager::findDirectory);
        }

        /**
         * Adds all subdirectories of {@code dirToSearchRecursively} to {@code sourceRoots}.
         *
         * @param sourceRoots an accumulator set that collects all subdirectories of {@code dirToSearchRecursively}.
         * @param dirToSearchRecursively
         */
        private void addContexts(LinkedHashSet<VirtualFile> sourceRoots, VirtualFile dirToSearchRecursively) {
            if (!dirToSearchRecursively.isDirectory()) return;
            if (dirToSearchRecursively.getName().startsWith(".")) return; //don't care about intellij IDEA private folders
            VfsUtilCore.visitChildrenRecursively(dirToSearchRecursively, new VirtualFileVisitor() {
                @Override
                public boolean visitFile(@NotNull VirtualFile file) {
                    if (file.isDirectory()) sourceRoots.add(file);
                    return super.visitFile(file);
                }
            });
        }

        @Override
        protected Condition<PsiFileSystemItem> getReferenceCompletionFilter() {
            return RES_FILE_FILTER;
        }

        @Nullable
        @Override
        public PsiFileSystemItem resolve() {
            return isAbsolutePathReference() ? null : super.resolve();
        }

        @NotNull
        @Override
        public FileReference createFileReference(TextRange range, int index, String text) {
            //TODO TODO: Keep an eye on the bit below where I tack on the ext. Doesn't seem particularly kosher.
            //where that gets added otherwise is anyone's guess..
            return new ResModuleReference(this, range, index, text + "." + RESOLVEFileType.INSTANCE.getDefaultExtension());
        }
    }
}
