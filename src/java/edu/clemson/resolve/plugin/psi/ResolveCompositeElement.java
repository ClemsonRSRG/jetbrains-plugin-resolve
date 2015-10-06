package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;

public interface ResolveCompositeElement extends PsiElement {
    public boolean shouldGoDeeper();
}
