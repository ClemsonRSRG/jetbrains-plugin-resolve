package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResOperationWithBodyNode;
import org.jetbrains.annotations.NotNull;

public abstract class ResOperationLikeNodeImpl
        extends
            ResNamedElementImpl implements ResOperationWithBodyNode {

    public ResOperationLikeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {

        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }
}
