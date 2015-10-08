package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResScopeProcessor;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResScopeProcessorBase;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResTypeProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResTypeReference
        extends
            PsiPolyVariantReferenceBase<ResTypeReferenceExpression> {

    public ResTypeReference(@NotNull ResTypeReferenceExpression o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResTypeReference)
                            psiPolyVariantReferenceBase).resolveInner();
                }
            };

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject())
                    .resolveWithCaching(this, MY_RESOLVER, false, false)
                : ResolveResult.EMPTY_ARRAY;
    }

    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResTypeReferenceExpression qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return processQualifierExpression(
                    ((ResFile)file), qualifier, processor, state);
        }
        return processUnqualifiedResolve(
                ((ResFile) file), processor, state, true);
    }

    @NotNull private ResTypeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResTypeProcessor(myElement, processor.isCompletion());
    }

    private static boolean processQualifierExpression(@NotNull ResFile file,
                                                      @NotNull ResTypeReferenceExpression qualifier,
                                                      @NotNull ResScopeProcessor processor,
                                                      @NotNull ResolveState state) {
       /* PsiElement target = qualifier.getReference().resolve();
        if (target == null || target == qualifier) return false;
        if (target instanceof GoImportSpec) target = ((GoImportSpec)target).getImportString().resolve();
        if (target instanceof PsiDirectory) {
            GoReference.processDirectory((PsiDirectory)target, file, null, processor, state, false);
        }*/
        return false;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolutionUtil.treeWalkUp(myElement, delegate);
        Collection<? extends ResNamedElement> result = delegate.getVariants();

        return false;
    }
}
