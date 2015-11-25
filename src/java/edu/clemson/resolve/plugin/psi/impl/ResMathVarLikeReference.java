package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
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
            return false;
            //return processQualifierExpression(((ResFile)file), qualifier,
            //        processor, state);
        }
        return processUnqualifiedResolve(((ResFile)file), processor, state, true);
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        Collection<? extends ResNamedElement> result = delegate.getVariants();

        //this processes any named elements we've found searching up the tree in the previous line
        if (!processLocalEntities(processor, state, result, localResolve)) return false;
        if (!processModuleLevelEntities(file, processor, state, localResolve)) return false;
        //if (ResReference.processUsesRequests(file, processor, state, myElement)) return false;

        return true;
    }

    private boolean processModuleLevelEntities(@NotNull ResFile file,
                                               @NotNull ResScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               boolean localProcessing) {
        if (!processLocalEntities(processor, state, file.getMathDefSigs(), localProcessing)) return false;
        //type families as well perhaps (if we're in the proper context -- e.g.: not a precis or precis-extension)
        //if (!processLocalEntities(processor, state, file.getTypes(), localProcessing)) return false;
        return true;
    }

    private boolean processLocalEntities(@NotNull PsiScopeProcessor processor,
                                         @NotNull ResolveState state,
                                         @NotNull Collection<? extends ResNamedElement> elements,
                                         boolean localResolve) {
        for (ResNamedElement e : elements) {
            if ((e.isPublic() || localResolve) && !processor.execute(e, state)) {
                return false;
            }
        }
        return true;
    }

    @NotNull private ResMathVarLikeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResMathVarLikeProcessor(myElement, processor.isCompletion());
    }

    protected static class ResMathVarLikeProcessor
            extends
                ResScopeProcessorBase {
        public ResMathVarLikeProcessor(@NotNull ResMathSymbolRefExp origin,
                                       boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }
        @Override protected boolean condition(@NotNull PsiElement element) {
            return true;
        }
    }
}
