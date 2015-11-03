package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResTypeLikeNodeDecl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeLikeDeclImpl
        extends
            ResNamedElementImpl implements ResTypeLikeNodeDecl {

    public ResAbstractTypeLikeDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }
}
