package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResOperationLikeNode;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDecl;
import org.jetbrains.annotations.NotNull;

public abstract class ResOperationLikeNodeImpl
        extends
            ResNamedElementImpl implements ResOperationLikeNode {

    public ResOperationLikeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }

}
