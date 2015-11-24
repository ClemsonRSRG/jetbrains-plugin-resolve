package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResMathVarLikeReference
        extends
            PsiPolyVariantReferenceBase<ResMathSymbolRefExp> {

    public ResMathVarLikeReference(@NotNull ResMathSymbolRefExp o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache
            .PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull @Override public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResMathVarLikeReference)psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));

        if (result.isEmpty() && myElement.getParent() instanceof ResRecieverType) {
            PsiElement resolve = new ResReference(myElement).resolve();
            if (resolve != null) {
                return PsiElementResolveResult.createResults(resolve);
            }
        }

        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull private PsiElement getIdentifier() {
        return myElement.getIdentifier();
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        return ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResMathSymbolRefExp qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return processQualifierExpression(((ResFile)file), qualifier,
                    processor, state);
        }
        return processUnqualifiedResolve(((ResFile)file), processor, state, true);
    }
}
