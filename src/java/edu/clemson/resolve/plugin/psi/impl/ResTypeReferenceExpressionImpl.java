package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResTypeReferenceExpressionImpl
        extends
            AbstractResTypeImpl implements ResTypeReferenceExpression {

    public ResTypeReferenceExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @Nullable public PsiElement getIdentifier() {
        return findChildByType(ConstTokenTypes.ID);
    }

    @Nullable public PsiReference getReference() {
        if (getIdentifier() == null) return null;
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable public ResTypeReferenceExpression getQualifier() {
        return ResPsiImplUtil.getQualifier(this);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResTypeReferenceExpressionImpl(node);
        }
    }
}
