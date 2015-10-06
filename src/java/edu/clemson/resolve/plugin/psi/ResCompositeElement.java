package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;

public interface ResCompositeElement extends PsiElement {
    public boolean shouldGoDeeper();
}
