package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class ResNamedElementImpl extends ResCompositeElementImpl {

    public ResNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
