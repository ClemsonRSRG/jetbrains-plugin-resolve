package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResTypeReferenceExpressionImpl
        extends
            ResCompositeElementImpl implements ResTypeReferenceExpression {

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    @NotNull public PsiReference getReference() {
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable public ResTypeReferenceExpression getQualifier() {
        return ResPsiImplUtil.getQualifier(this);
    }
}
