package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResTypeLikeNodeDecl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeLikeNodeImpl
        extends
            ResNamedElementImpl implements ResTypeLikeNodeDecl {

    public ResAbstractTypeLikeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }


}
