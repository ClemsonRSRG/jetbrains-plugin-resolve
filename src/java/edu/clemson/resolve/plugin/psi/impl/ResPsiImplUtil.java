package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResPsiImplUtil {

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesItem o) {
        if (o.getTextLength() == 0) return PsiReference.EMPTY_ARRAY;
        return new ResUsesReferenceSet(o).getAllReferences();
    }

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesItem usesItem) {
        String text = usesItem.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
                TextRange.EMPTY_RANGE;
    }

    @NotNull public static PsiReference getReference(
            @NotNull ResTypeReferenceExpression o) {
        return new ResTypeReference(o);
    }

    @Nullable public static ResTypeReferenceExpression getQualifier(
            @NotNull ResTypeReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExpression.class);
    }

    @Nullable public static PsiFile resolve(@NotNull ResUsesItem usesItem) {
        PsiReference[] references = usesItem.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference = ((FileReferenceOwner)reference).getLastFileReference();
                PsiElement result = lastFileReference != null ? lastFileReference.resolve() : null;
                PsiFile x = result instanceof PsiFile ? (PsiFile)result : null;
                return x;
            }
        }
        return null;
    }
}
