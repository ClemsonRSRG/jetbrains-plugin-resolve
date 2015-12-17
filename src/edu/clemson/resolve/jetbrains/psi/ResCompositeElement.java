package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.PsiElement;

/** A {@link ResCompositeElement} represents an internal node to either a
 *  parse tree or a PSI tree. Thus this node is automatically be constructed for
 *  any intermediate, non-leaf children.
 */
public interface ResCompositeElement extends PsiElement {
    public boolean shouldGoDeeper();
}
