package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import org.jetbrains.annotations.NotNull;

public class ResCompositeElementImpl
        extends
            ASTWrapperPsiElement implements ResCompositeElement {

    public ResCompositeElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public String toString() {
        return getNode().getElementType().toString();
    }

    @Override public boolean shouldGoDeeper() {
        return true;
    }
}
