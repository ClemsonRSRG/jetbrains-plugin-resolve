package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResType;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResReferenceExpressionImpl
        extends
            ResCompositeElementImpl implements ResReferenceExpression {

    public ResReferenceExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    @NotNull public ResReference getReference() {
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable @Override public ResReferenceExpression getQualifier() {
        return null;
    }

    @Nullable public ResType getResType(ResolveState context) {
        System.out.println("DON'T FORGET TO IMPLEMENT getResType for ResReferenceExpressionImpl");
        return ResPsiImplUtil.getGoType(this, context);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResReferenceExpressionImpl(node);
        }
    }
}
