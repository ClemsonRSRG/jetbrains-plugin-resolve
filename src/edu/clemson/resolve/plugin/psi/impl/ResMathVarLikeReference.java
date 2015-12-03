package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.clemson.resolve.plugin.psi.impl.ResReference.processModuleLevelEntities;

public class ResMathVarLikeReference
        extends
            PsiPolyVariantReferenceBase<ResMathReferenceExp> {

    public ResMathVarLikeReference(@NotNull ResMathReferenceExp o) {
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
        ResMathReferenceExp qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return false;
            //return processQualifierExpression(((ResFile)file), qualifier,
            //        processor, state);
        }
        return processUnqualifiedResolve(((ResFile) file), processor, state, true);
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
        processMathParameterLikeThings(myElement, delegate);
        if (!processNamedElements(processor, state, delegate.getVariants(), localResolve)) return false;

        if (!processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processUsesRequests(file, processor, state, myElement)) return false;
        //processImplicitUsesRequests(
        if (!processBuiltin(processor, state, myElement)) return false;

        return true;
    }

    private boolean processBuiltin(@NotNull ResScopeProcessor processor,
                                  @NotNull ResolveState state,
                                  @NotNull ResCompositeElement element) {
        ResFile f = ResElementFactory.getHardCodedMathFile(element.getProject());
        return processModuleLevelEntities(f, processor, state, true);
    }

    private void processMathParameterLikeThings(@NotNull ResCompositeElement e,
                                                @NotNull ResScopeProcessorBase processor) {
        ResMathDefinitionDecl def =
                PsiTreeUtil.getParentOfType(e, ResMathDefinitionDecl.class);
        if (def != null) {
            processTopLevelMathDef(def, processor);
        }
    }

    private boolean processTopLevelMathDef(@NotNull ResMathDefinitionDecl o,
                                           @NotNull ResScopeProcessorBase processor) {
        List<ResMathDefinitionSignature> sigs = o.getSignatures();
        if (sigs.size() == 1) {
            ResMathDefinitionSignature sig = o.getSignatures().get(0);
            if (!processParameters(processor, sig.getParameters())) return false;
        } //size > 1 ? then we're categorical; size == 0, we're null
        return true;
    }

    private boolean processParameters(@NotNull ResScopeProcessorBase processor,
                                      @NotNull List<ResMathVarDeclGroup> parameters) {
        for (ResMathVarDeclGroup declaration : parameters) {
            if (!processNamedElements(processor, ResolveState.initial(), declaration.getMathVarDefList(), true)) return false;
           //if (!processImplicitTypeParameters(processor, ResolveState.initial(), declaration.getMathExp(), true)) return false;
        }
        return true;
    }

    private boolean processNamedElements(@NotNull PsiScopeProcessor processor,
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
        public Map<String, String> implicitlyBoundTypeParameters =
                new HashMap<String, String>();
        public ResMathVarLikeProcessor(@NotNull ResMathReferenceExp origin,
                                       boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }
        @Override protected boolean crossOff(@NotNull PsiElement element) {

            return false;
        }
    }
}