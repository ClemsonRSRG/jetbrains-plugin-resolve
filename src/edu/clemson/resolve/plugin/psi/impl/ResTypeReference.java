package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.util.ArrayUtil;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by daniel on 10/16/15.
 */
public class ResTypeReference extends PsiPolyVariantReferenceBase<ResTypeReferenceExpression> {
    public ResTypeReference(ResTypeReferenceExpression psiElement) {
        super(psiElement);
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        return new ResolveResult[0];
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }
}
