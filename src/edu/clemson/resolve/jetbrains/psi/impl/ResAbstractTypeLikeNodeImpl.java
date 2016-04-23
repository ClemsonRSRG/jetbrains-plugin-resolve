package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.ResMathExp;
import edu.clemson.resolve.jetbrains.psi.ResTypeLikeNodeDecl;
import edu.clemson.resolve.jetbrains.psi.ResTypeModelDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractTypeLikeNodeImpl
        extends
        ResNamedElementImpl implements ResTypeLikeNodeDecl {

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
        } else { //we must be a representation
            return null;
        }
    }

}
