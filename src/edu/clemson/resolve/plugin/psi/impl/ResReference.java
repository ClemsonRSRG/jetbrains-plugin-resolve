package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import org.jetbrains.annotations.NotNull;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpressionBase> {

    public ResReference(ResReferenceExpressionBase psiElement) {
        super(psiElement);
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        return new ResolveResult[0];
    }

    @NotNull @Override public Object[] getVariants() {
        return new Object[0];
    }
}
