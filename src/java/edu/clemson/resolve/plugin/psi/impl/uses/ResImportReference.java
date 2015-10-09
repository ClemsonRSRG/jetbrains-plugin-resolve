package edu.clemson.resolve.plugin.psi.impl.uses;

import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResImportReference extends FileReference {

    public ResImportReference(@NotNull FileReferenceSet fileReferenceSet,
                              TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @NotNull @Override protected ResolveResult[] innerResolve(
            boolean caseSensitive, @NotNull PsiFile file) {
        //first, go all the way up to the psiDirectory housing the whole project
        String projectName = file.getProject().getName();
        PsiDirectory currentDir = file.getContainingDirectory();
        if (currentDir == null) return ResolveResult.EMPTY_ARRAY;
        String currentDirName = currentDir.getName();
        if (currentDirName == null) return ResolveResult.EMPTY_ARRAY;
        while (!currentDirName.equals(projectName)) {

        }
        PsiFile x = currentDir.findFile(getText() + ".resolve");
        //file.getContainingDirectory().findFile()

        file.getProject().getName();
  /*  if (isFirst()) {
      if (".".equals(getCanonicalText())) {
        PsiDirectory directory = getDirectory();
        return directory != null ? new PsiElementResolveResult[]{new PsiElementResolveResult(directory)} : ResolveResult.EMPTY_ARRAY;
      }
      else if ("..".equals(getCanonicalText())) {
        PsiDirectory directory = getDirectory();
        PsiDirectory grandParent = directory != null ? directory.getParentDirectory() : null;
        return grandParent != null ? new PsiElementResolveResult[]{new PsiElementResolveResult(grandParent)} : ResolveResult.EMPTY_ARRAY;
      }
    }*/

        if (isLast()) {
            List<ResolveResult> filtered = ContainerUtil.filter(super.innerResolve(caseSensitive, file), new Condition<ResolveResult>() {
                @Override
                public boolean value(@NotNull ResolveResult resolveResult) {
                    PsiElement element = resolveResult.getElement();
                    return element != null && element instanceof PsiDirectory;
                }
            });
            return filtered.toArray(new ResolveResult[filtered.size()]);
        }
        return super.innerResolve(caseSensitive, file);
    }

    @Nullable private PsiDirectory getDirectory() {
        PsiElement originalElement = CompletionUtil.getOriginalElement(getElement());
        PsiFile file = originalElement != null ? originalElement.getContainingFile() : getElement().getContainingFile();
        return file.getParent();
    }
}
