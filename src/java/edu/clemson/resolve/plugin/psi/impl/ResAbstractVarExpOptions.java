package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.ResExpression;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class ResAbstractVarExpOptions extends ResCompositeElementImpl {

    public ResAbstractVarExpOptions(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public ResExpression getExpression() {
        return findNotNullChildByClass(ResExpression.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResAbstractVarExpOptions(node);
        }
    }
}
