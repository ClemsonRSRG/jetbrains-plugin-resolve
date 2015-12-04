package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResTypeNodeDecl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeNodeImpl
        extends
            ResNamedElementImpl implements ResTypeNodeDecl {

    public ResAbstractTypeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }


}
