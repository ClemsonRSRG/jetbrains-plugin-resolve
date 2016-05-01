package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceCompletionImpl;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.ResModuleIdentifier;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

/** Represents a collection of file references along with some number of underlying default file searching
 *  contexts (folders, directories, etc).
 *  <p>
 *  This is a companion class
 *  to {@link ResModuleIdentifierReference}; so don't be fooled by the seeming lack of connection between the two. The
 *  main thing I do here is implement {@link #getDefaultContexts()}, which ultimately influences which folders
 *  (contexts) are searched through by the {@link ResModuleIdentifierReference#innerResolve(boolean, PsiFile)} method.
 *
 *  @author dtwelch
 */
public class ResModuleIdentifierReferenceSet extends FileReferenceSet {

    /** Apparently overriding {@link ResModuleIdentifierReference#createLookupItem(com.intellij.psi.PsiElement)} isn't
     *  enough to keep things like {@link PsiDirectory} and other {@link PsiFile}s from getting included in completions
     *  carried out by {@link FileReferenceCompletionImpl#getFileReferenceCompletionVariants(FileReference)} happening
     *  in the background.
     *  <p>
     *  Turns out you need to override {@link #getReferenceCompletionFilter()} return this {@link Condition} instance.
     *  How exactly clients
     *  (e.g., me) were supposed to figure this out without any sort of documentation whatsoever is beyond me; seems
     *  that the {@link FileReference} stuff overall is pretty light on documentation. oh well.
     */
    private static final Condition<PsiFileSystemItem> RES_FILE_FILTER = item -> item instanceof ResFile;

    public ResModuleIdentifierReferenceSet(@NotNull ResModuleIdentifier moduleIdentifier) {
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

        //this will get top level files for *both* the STD and RESOLVEPATH srcs
        //LinkedHashSet<VirtualFile> sourceRoots = RESOLVESdkUtil.getSourcesPathsToLookup(project, module);

        //but only add std stuff for now..
        LinkedHashSet<VirtualFile> sourceRoots = newLinkedHashSet();
        //this adds the std libs /usr/local/resolve/std

        VirtualFile rootSdkDir = RESOLVESdkUtil.getSdkSrcDir(project, module);
        ContainerUtil.addIfNotNull(sourceRoots, rootSdkDir);

        //add all contexts (subdirectories) we wanna search
        if (rootSdkDir != null && rootSdkDir.isDirectory()) {
            for (VirtualFile v : rootSdkDir.getChildren()) {
                if (v.isDirectory()) sourceRoots.add(v);
            }
        }
        return ContainerUtil.mapNotNull(sourceRoots, psiManager::findDirectory);
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
        return new ResModuleIdentifierReference(this, range, index, text + "." + RESOLVEFileType.INSTANCE.getDefaultExtension());
    }
}
