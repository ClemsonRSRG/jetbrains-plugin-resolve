package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.ResTypeLikeNodeDecl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeLikeNodeImpl
        extends
            ResNamedElementImpl implements ResTypeLikeNodeDecl {

    public ResAbstractTypeLikeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }

}
