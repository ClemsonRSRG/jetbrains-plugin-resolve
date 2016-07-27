package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.jetbrains.psi.ResEnsuresClause;
import edu.clemson.resolve.jetbrains.psi.ResOperationLikeNode;
import edu.clemson.resolve.jetbrains.psi.ResRequiresClause;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractOperationLikeNode extends ResNamedElementImpl implements ResOperationLikeNode {

    public ResAbstractOperationLikeNode(@NotNull ASTNode node) {
        super(node);
    }

    //TODO:
    //yes, these will be null for procedure decls, though technically those guys shouldn't even extend ResAbstractOperationLikeNode
    //or implement (ResOperationLikeNode) for that matter.
    @Nullable
    public ResRequiresClause getRequiresClause() {
        return findChildByClass(ResRequiresClause.class);
    }

    @Nullable
    public ResEnsuresClause getEnsuresClause() {
        return findChildByClass(ResEnsuresClause.class);
    }
}
