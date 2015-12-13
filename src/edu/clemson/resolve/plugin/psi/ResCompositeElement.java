package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;

/**
 * An {@link ResCompositeElement} represents an internal node to either a
 * parse tree or a PSI tree. Thus this node is automatically be constructed for
 * any intermediate, non-leaf children.
 */
public interface ResCompositeElement extends PsiElement {
    public boolean shouldGoDeeper();
}
