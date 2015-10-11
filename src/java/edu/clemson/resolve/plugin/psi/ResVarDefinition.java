package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.annotations.NotNull;

public interface ResVarDefinition extends ResNamedElement {

    @NotNull PsiElement getIdentifier();
}
