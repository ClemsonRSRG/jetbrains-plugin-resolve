package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResOperationLikeNode;
import edu.clemson.resolve.plugin.psi.ResOperationLikeParameters;
import edu.clemson.resolve.plugin.psi.ResParamDecl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ResAbstractOperationLikeNode
        extends
            ResNamedElementImpl implements ResOperationLikeNode {

    public ResAbstractOperationLikeNode(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public List<ResParamDecl> getParameters() {
        List<ResParamDecl> result = new ArrayList<ResParamDecl>();
        ResOperationLikeParameters params =
                findChildByClass(ResOperationLikeParameters.class);
        if (params == null) return result;
        result.addAll(params.getParamDeclList());
        return result;
    }
}
