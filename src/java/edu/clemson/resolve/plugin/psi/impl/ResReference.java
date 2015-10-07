package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpressionBase> {
    //doing types should be similar to the way go handles struct field accesses...

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(@NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase, boolean incompleteCode) {
                    return ((ResReference)psiPolyVariantReferenceBase).resolveInner();
                }
            };

    public ResReference(@NotNull ResReferenceExpressionBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    //this is the big starting point (I think...)
    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @Override @NotNull public ResolveResult[] multiResolve(
            boolean incompleteCode) {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        return ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

}
