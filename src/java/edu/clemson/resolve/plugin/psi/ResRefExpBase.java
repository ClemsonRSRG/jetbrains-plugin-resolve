package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResRefExpBase extends ResCompositeElement {

    @NotNull public PsiElement getIdentifier();

    @Nullable public ResRefExpBase getQualifier();
}
