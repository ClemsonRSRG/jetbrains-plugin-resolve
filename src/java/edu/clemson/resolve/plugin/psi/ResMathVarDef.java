package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface ResMathVarDef extends ResNamedElement {

    @NotNull public PsiElement getIdentifier();
}
