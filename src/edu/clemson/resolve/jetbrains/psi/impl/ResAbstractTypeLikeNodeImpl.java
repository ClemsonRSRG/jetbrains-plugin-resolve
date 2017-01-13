package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractTypeLikeNodeImpl extends ResNamedElementImpl implements ResTypeLikeNodeDecl {

    public ResAbstractTypeLikeNodeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }

    @Nullable
    @Override
    public ResMathExp getMathMetaTypeExp() {
        if (this instanceof ResTypeModelDecl) {
            return ((ResTypeModelDecl) this).getMathExp();
        }
        else {
            ResTypeReprDecl x = (ResTypeReprDecl) this;
            ResType type = x.getType();
            //TODO: Somehow we need to figure out how to transform a Record into a mathematical cartesian product type...
            //we must be a representation
            return null;
        }
    }

}
