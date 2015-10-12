package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.ResType;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeDecl extends ResNamedElementImpl {

    public ResAbstractTypeDecl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public ResType getType() {
        return findNotNullChildByClass(ResType.class);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstEleTypes.ID);
    }
}
