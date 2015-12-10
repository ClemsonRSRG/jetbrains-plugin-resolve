package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.formatter.FormatterUtil;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//TODO: Thinking ahead to type appearances in concept/enhancement realizations,
//we might just need to have to specify that we DONT want to search the
//spec we're enhancing (and that should be easy since we already have a specialized
//processor for types, or b.) somehow ignore type models when we'r
public class ResTypeReference
        extends
            PsiPolyVariantReferenceBase<ResTypeReferenceExp> {

    public ResTypeReference(@NotNull ResTypeReferenceExp o) {
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
                    return ((ResTypeReference)psiPolyVariantReferenceBase).resolveInner();
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

    @Override @NotNull public ResolveResult[] multiResolve(
            boolean incompleteCode) {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false)
                : ResolveResult.EMPTY_ARRAY;
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResTypeReferenceExp qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return processQualifierExpression(((ResFile)file), qualifier, processor, state);
        }
        return processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }

    private static boolean processQualifierExpression(@NotNull ResFile file,
                                                      @NotNull ResTypeReferenceExp qualifier,
                                                      @NotNull ResScopeProcessor processor,
                                                      @NotNull ResolveState state) {
        PsiReference targetRef = qualifier.getReference();
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
        if (!ResReference.processNamedElements(processor, state, result, localResolve)) return false;

        if (!ResReference.processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processUsesRequests(file, processor, state, myElement, false)) return false;
        return true;
    }

    /*private boolean processModuleLevelEntities(@NotNull ResFile file,
                                               @NotNull ResScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               boolean localProcessing) {
        if (!processNamedElements(processor, state, file.getFacilities(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getTypes(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getGenericTypeParams(), localProcessing)) return false;
        return true;
    }*/

    /*private boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                         @NotNull ResolveState state,
                                         @NotNull Collection<? extends ResNamedElement> elements,
                                         boolean localResolve) {
        for (ResNamedElement definition : elements) {
            //if (definition instanceof ResTypeLikeDecl && !allowed((ResTypeLikeDecl)definition)) continue;
            if ((definition.isPublic() || localResolve) && !processor.execute(definition, state)) return false;
        }
        return true;
    }*/

    @NotNull private ResTypeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResTypeProcessor(myElement, processor.isCompletion());
    }

    /** A processor for treewalking */
    protected static class ResTypeProcessor extends ResScopeProcessorBase {

        public ResTypeProcessor(@NotNull ResTypeReferenceExp origin,
                                boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }
        @Override protected boolean crossOff(@NotNull PsiElement e) {
            return e instanceof ResTypeLikeNodeDecl ||
                    e instanceof ResFacilityDecl ||
                    e instanceof ResTypeParamDecl;
        }
    }
}
