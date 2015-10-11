package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.ResReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResType;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResReferenceExpressionImpl
        extends
            AbstractResExpressionImpl implements ResReferenceExpression {

    public ResReferenceExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @Nullable public PsiElement getIdentifier() {
        return findChildByType(ConstEleTypes.ID);
    }

    @Nullable public ResReference getReference() {
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable @Override public ResReferenceExpression getQualifier() {
        return null;
    }

    @Nullable public ResType getResType(ResolveState context) {
        System.out.println("DON'T FORGET TO IMPLEMENT getResType for ResReferenceExpressionImpl");
        return ResPsiImplUtil.getResType(this, context);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResReferenceExpressionImpl(node);
        }
    }
}
