package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static edu.clemson.resolve.jetbrains.psi.impl.ResReference.processNamedElements;
import static edu.clemson.resolve.jetbrains.psi.impl.ResReference.processSuperModules;

//TODO: Thinking ahead to type appearances in concept/enhancement realizations,
//we might just need to have to specify that we DONT want to search the
//spec we're enhancing (and that should be easy since we already have a specialized
//processor for types, or b.) somehow ignore type models when we'r
public class ResTypeReference
        extends
        PsiPolyVariantReferenceBase<ResTypeReferenceExp> {

    ResTypeReference(@NotNull ResTypeReferenceExp o) {
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
                    return ((ResTypeReference) psiPolyVariantReferenceBase).resolveInner();
                }
            };

    @NotNull
    private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull
    private PsiElement getIdentifier() {
        return myElement.getIdentifier();
    }

    @Override
    @NotNull
    public ResolveResult[] multiResolve(
            boolean incompleteCode) {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false)
                : ResolveResult.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResTypeReferenceExp qualifier = myElement.getQualifier();
         if (qualifier != null) {
            return processQualifierExpression(((ResFile) file), qualifier, processor, state);
        }
        return processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }

    private static boolean processQualifierExpression(@NotNull ResFile file,
                                                      @NotNull ResTypeReferenceExp qualifier,
                                                      @NotNull ResScopeProcessor processor,
                                                      @NotNull ResolveState state) {
        //TODO: Ok dan, It's too late to deal with this now. But ResTypeReferenceExp *should*
        //have a resolve method, but doesn't atm for some reason. Check it out again tomorrow .
        PsiReference targetRef = qualifier.resolve();
        PsiElement target = targetRef.resolve();
        if (target == null || target == qualifier) return false;
        if (target instanceof ResFacilityDecl) {
            ResFile specFile = ((ResFacilityDecl) target).getSpecification();
            if (specFile != null) {
                ResReference.processModuleLevelEntities(specFile, processor, state, false);
            }
        }
        return false;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        Collection<? extends ResNamedElement> result = delegate.getVariants();
        //this processes any named elements we've found searching up the tree in the previous line
        if (!processNamedElements(processor, state, result, localResolve)) return false;
        if (!ResReference.processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processExplicitlyNamedAndInheritedUsesRequests(file, processor, state)) return false;

        //TODO: What we really need to avoid finding both the models and reprs
        // is some flag, say, "stopAfterFirst" (I think...)
        if (!processTypeSuperModules(file, processor, state)) return false;
        return true;
    }

    private boolean processTypeSuperModules(@NotNull ResFile file,
                                            @NotNull ResScopeProcessor processor,
                                            @NotNull ResolveState state) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        processSuperModules(file, processor, delegate, state);
        return processNamedElements(processor, state, delegate.getVariants(), false);
    }

    @NotNull
    private ResTypeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResTypeProcessor(myElement, processor.isCompletion());
    }

    /**
     * A processor for treewalking
     */
    protected static class ResTypeProcessor extends ResScopeProcessorBase {

        public ResTypeProcessor(@NotNull ResTypeReferenceExp origin,
                                boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }

        @Override
        protected boolean crossOff(@NotNull PsiElement e) {
            return !(e instanceof ResTypeLikeNodeDecl) &&
                    !(e instanceof ResFacilityDecl) &&
                    !(e instanceof ResTypeParamDecl);
        }
    }
}
