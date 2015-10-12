package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface ResFacilityDecl extends ResNamedElement, ResCompositeElement {

    @NotNull PsiElement getIdentifier();

    @NotNull ResUsesItem getSpec();

}
