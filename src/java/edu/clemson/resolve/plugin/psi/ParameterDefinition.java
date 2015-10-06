package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface ParameterDefinition extends NamedElement {

    @NotNull PsiElement getIdentifier();
}
