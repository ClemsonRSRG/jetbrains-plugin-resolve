package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResMathSymbolRefExp extends ResRefExpBase {

    @NotNull public PsiElement getIdentifier();

    @NotNull public PsiReference getReference();

    @Nullable public ResMathSymbolRefExp getQualifier();

}
