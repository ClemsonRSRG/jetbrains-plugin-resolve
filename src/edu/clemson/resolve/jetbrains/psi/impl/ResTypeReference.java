package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResTypeReference extends PsiPolyVariantReferenceBase<ResTypeReferenceExp> {

    ResTypeReference(@NotNull ResTypeReferenceExp o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(), o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(@NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                                               boolean incompleteCode) {
                    return ((ResTypeReference) psiPolyVariantReferenceBase).resolveInner();
                }
            };

    @NotNull
    private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull
    private PsiElement getIdentifier() {
        return myElement.getIdentifier();
    }

    @Override
    @NotNull
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject()).resolveWithCaching(this, MY_RESOLVER, false, false)
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
        PsiElement target = qualifier.resolve();
        if (target == null || target == qualifier) return false;
        if (target instanceof ResFacilityDecl) {
            ResFile specFile = ((ResFacilityDecl) target).resolveSpecification();
            if (specFile != null) ResReference.processModuleLevelEntities(specFile, processor, state, false, true);
        }
        else if (target instanceof ResFile) {
            ResReference.processModuleLevelEntities((ResFile) target, processor, state, false);
        }
        return false;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        if (!ResReference.processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processUsesImports(file, processor, state)) return false;
        if (!ResReference.processSuperModulesUsesImports(file, processor, state)) return false;
        return true;
    }

    @NotNull
    private ResTypeProcessor createDelegate(@NotNull ResScopeProcessor processor) {
        return new ResTypeProcessor(myElement, processor.isCompletion());
    }

    /** A processor for treewalking */
    private static class ResTypeProcessor extends ResScopeProcessorBase {

        ResTypeProcessor(@NotNull ResTypeReferenceExp origin, boolean completion) {
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
