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
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ResImportReference extends FileReference {

    public ResImportReference(@NotNull FileReferenceSet fileReferenceSet,
                              TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @Override protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof PsiDirectory) {
            return RESOLVECompletionUtil
                    .createDirectoryLookupElement((PsiDirectory) candidate);
        }
        return super.createLookupItem(candidate);
    }

    public PsiFileSystemItem resolve() {
        PsiDirectory sourceFile = getDirectory();
        Collection<PsiFileSystemItem> contexts =
                this.getFileReferenceSet().getDefaultContexts();

        for (PsiFileSystemItem f : contexts) {
            String text = getText();
            VirtualFile x = f.getVirtualFile().findChild(getText()+".resolve");
            if (x == null) continue;
            PsiFile file = f.getManager().findFile(x);
            if (file != null) {
                PsiElementResolveResult result =
                        new PsiElementResolveResult(FileReference.getOriginalFile(file));
                return (PsiFileSystemItem) result.getElement();
            }
        }
        return null;
    }

    /*@NotNull @Override protected ResolveResult[] innerResolve(
            boolean caseSensitive, @NotNull PsiFile file) {

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
    }*/

    @Nullable private PsiDirectory getDirectory() {
        PsiElement originalElement = CompletionUtil.getOriginalElement(getElement());
        PsiFile file = originalElement != null ? originalElement.getContainingFile() : getElement().getContainingFile();
        return file.getParent();
    }
}
