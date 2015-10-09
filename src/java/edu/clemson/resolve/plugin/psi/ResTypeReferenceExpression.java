package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResTypeReferenceExpression
        extends
        ResReferenceExpressionBase {

    @Nullable PsiElement getIdentifier();

    @Nullable PsiReference getReference();

    @Nullable
    ResTypeReferenceExpression getQualifier();
}
