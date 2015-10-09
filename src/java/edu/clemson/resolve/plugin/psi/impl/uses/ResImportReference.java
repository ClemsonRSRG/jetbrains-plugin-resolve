package edu.clemson.resolve.plugin.psi.impl.uses;

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
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResImportReference extends FileReference {

    public ResImportReference(@NotNull FileReferenceSet fileReferenceSet,
                              TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @NotNull @Override protected ResolveResult[] innerResolve(
            boolean caseSensitive, @NotNull PsiFile file) {
        if (isLast()) {
            List<ResolveResult> filtered =
                    ContainerUtil.filter(super.innerResolve(caseSensitive, file),
                            new Condition<ResolveResult>() {
                @Override
                public boolean value(@NotNull ResolveResult resolveResult) {
                    PsiElement element = resolveResult.getElement();
                    return element != null && element instanceof PsiFile;
                }
            });
            return filtered.toArray(new ResolveResult[filtered.size()]);
        }
        return super.innerResolve(caseSensitive, file);
    }

}
