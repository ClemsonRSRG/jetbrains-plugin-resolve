package edu.clemson.resolve.plugin.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResUsesItem extends ResCompositeElement {

    @Nullable PsiElement getIdentifier();

    @NotNull PsiReference[] getReferences();

    @NotNull @Override PsiReference getReference();

    @Nullable PsiFile resolve();

    @NotNull TextRange getUsesTextRange();
}
