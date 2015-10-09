package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResReferenceExpressionBase extends ResCompositeElement {

    @Nullable PsiElement getIdentifier();

    @Nullable ResReferenceExpressionBase getQualifier();
}
