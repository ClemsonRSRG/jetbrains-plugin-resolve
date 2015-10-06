package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeReferenceExpression
        extends
        ReferenceExpressionBase {

    @NotNull PsiElement getIdentifier();

    @NotNull PsiReference getReference();

    @Nullable
    TypeReferenceExpression getQualifier();
}
