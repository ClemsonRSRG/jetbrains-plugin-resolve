package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.jetbrains.psi.ResModuleLibraryIdentifier;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a reference to some library directory. This is distinct from {@link ResModuleReference}, which
 * is otherwise used to reference a specific {@link edu.clemson.resolve.jetbrains.psi.ResFile}.
 *
 * @author dtwelch
 */
public class ResModuleLibraryReference extends FileReference {

    ResModuleLibraryReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive, @NotNull PsiFile file) {
        String referenceText = getText();
        Set<ResolveResult> result = ContainerUtil.newLinkedHashSet();
        Set<ResolveResult> innerResult = ContainerUtil.newLinkedHashSet();
        Collection<PsiFileSystemItem> ctxs = getContexts();
        for (PsiFileSystemItem context : getContexts()) {
            innerResolveInContext(referenceText, context, innerResult, caseSensitive);
            for (ResolveResult resolveResult : innerResult) {
                PsiElement element = resolveResult.getElement();
                if (element instanceof PsiDirectory) {
                    if (isLast()) { //TODO: hmm..
                        return new ResolveResult[]{resolveResult};
                    }
                    result.add(resolveResult);
                }
            }
            innerResult.clear();
        }
        return result.isEmpty() ? ResolveResult.EMPTY_ARRAY : result.toArray(new ResolveResult[result.size()]);
    }

    @Override
    protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof PsiDirectory) {
            return RESOLVECompletionUtil.createDirectoryLookupElement((PsiDirectory) candidate);
        }
        return super.createLookupItem(candidate);
    }

    //TODO: IsReferenceTo  needs to eventually get implemented for fileReference renaming to work
    //(I think BindsTo()? sp? needs to as well.

    public static class ResModuleLibraryReferenceSet extends FileReferenceSet {
        public ResModuleLibraryReferenceSet(@NotNull ResModuleLibraryIdentifier libraryIdentifier) {
            super(libraryIdentifier.getText(), libraryIdentifier,
                    libraryIdentifier.getModuleLibraryIdentiferTextRange().getStartOffset(), null, true);
        }

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
            LinkedHashSet<VirtualFile> sourceRoots = RESOLVESdkUtil.getSourcesPathsToLookup(project, module);
            //all this does is transform each VirtualFile (which better be directory--see above loop) into PsiDirectory
            return ContainerUtil.mapNotNull(sourceRoots, psiManager::findDirectory);
        }

        @NotNull
        @Override
        public FileReference createFileReference(TextRange range, int index, String text) {
            return new ResModuleLibraryReference(this, range, index, text);
        }

        @Override
        protected Condition<PsiFileSystemItem> getReferenceCompletionFilter() {
            return DIRECTORY_FILTER;
        }

        @Override
        public String getSeparatorString() {
            return ".";
        }
    }
}
