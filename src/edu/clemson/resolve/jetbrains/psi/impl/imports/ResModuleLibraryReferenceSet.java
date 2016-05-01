package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.ResModuleLibraryIdentifier;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public class ResModuleLibraryReferenceSet extends FileReferenceSet {

    public ResModuleLibraryReferenceSet(@NotNull ResModuleLibraryIdentifier libraryIdentifier) {
        super(libraryIdentifier.getText(), libraryIdentifier,
                libraryIdentifier.getModuleLibraryIdentiferTextRange().getStartOffset(), null, true);
    }

    @NotNull
    @Override
    public Collection<PsiFileSystemItem> computeDefaultContexts() {
        PsiFile file = getContainingFile();
        if (file == null || !file.isValid() || isAbsolutePathReference()) return Collections.emptyList();

        final PsiManager psiManager = file.getManager();
        Module module = ModuleUtilCore.findModuleForPsiElement(file);
        Project project = file.getProject();

        //this will get top level files for *both* the STD and RESOLVEPATH srcs
        LinkedHashSet<VirtualFile> sourceRoots = RESOLVESdkUtil.getSourcesPathsToLookup(project, module);

        //now I add them + their one level deep interior files into a fresh set
        LinkedHashSet<VirtualFile> rootsPlusDirsOneDeep = new LinkedHashSet<>(sourceRoots);
        for (VirtualFile u : sourceRoots) {
            for (VirtualFile v : u.getChildren()) {
                if (v.isDirectory()) rootsPlusDirsOneDeep.add(v);
            }
        }
        //all this does is transform each VirtualFile (which better be directory--see above loop) into PsiDirectory
        return ContainerUtil.mapNotNull(rootsPlusDirsOneDeep, psiManager::findDirectory);
    }

    @Override
    protected Condition<PsiFileSystemItem> getReferenceCompletionFilter() {
        return DIRECTORY_FILTER;
    }

    @Nullable
    @Override
    public PsiFileSystemItem resolve() {
        return isAbsolutePathReference() ? null : super.resolve();
    }

    @NotNull
    @Override
    public FileReference createFileReference(TextRange range, int index, String text) {
        return new ResModuleLibraryReference(this, range, index, text);
    }
}
