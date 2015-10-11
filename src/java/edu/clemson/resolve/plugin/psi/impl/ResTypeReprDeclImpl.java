package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResTypeReprDecl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class ResTypeReprDeclImpl extends ResAbstractTypeDecl
         implements ResTypeReprDecl {

    public ResTypeReprDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public ResType getType() {
        return findNotNullChildByClass(ResType.class);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstEleTypes.ID);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResTypeReprDeclImpl(node);
        }
    }
}
