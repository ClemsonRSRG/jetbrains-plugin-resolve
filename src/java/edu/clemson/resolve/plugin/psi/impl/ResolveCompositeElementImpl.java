package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResolveCompositeElement;
import org.jetbrains.annotations.NotNull;

public class ResolveCompositeElementImpl
        extends
            ASTWrapperPsiElement implements ResolveCompositeElement {

    public ResolveCompositeElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public String toString() {
        return getNode().getElementType().toString();
    }

    @Override public boolean shouldGoDeeper() {
        return true;
    }
}
