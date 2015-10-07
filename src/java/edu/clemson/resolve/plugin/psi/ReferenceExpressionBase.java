package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ReferenceExpressionBase extends ResCompositeElement {

    @NotNull PsiElement getIdentifier();

    @Nullable
    ReferenceExpressionBase getQualifier();
}