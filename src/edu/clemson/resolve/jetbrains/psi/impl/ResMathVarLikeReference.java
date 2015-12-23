package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static edu.clemson.resolve.jetbrains.psi.impl.ResReference.processModuleLevelEntities;
import static edu.clemson.resolve.jetbrains.psi.impl.ResReference.processParameterLikeThings;

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

        PsiElement parent = myElement.getParent();
        if (parent instanceof ResMathSelectorExp) {
            boolean result = processMathSelector((ResMathSelectorExp)parent, processor, state, myElement);
            if (processor.isCompletion()) return result;
            if (!result || ResPsiImplUtil.prevDot(myElement)) return false;
        }

        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        Collection<? extends ResNamedElement> result = delegate.getVariants();

        //this processes any named elements we've found searching up the tree in the previous line
        if (!processNamedElements(processor, state, result, localResolve)) return false;
        ResReference.processParameterLikeThings(myElement, delegate);
        if (!processNamedElements(processor, state, delegate.getVariants(), localResolve)) return false;

        if (!processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processExplicitlyNamedAndInheritedUsesRequests(file, processor, state)) return false;
        if (!processSuperModules(file, processor, state)) return false;
        if (!processBuiltin(processor, state, myElement)) return false;
        return true;
    }

    private boolean processSuperModules(@NotNull ResFile file,
                                        @NotNull ResScopeProcessor processor,
                                        @NotNull ResolveState state) {
        for(ResModuleSpec spec : file.getSuperModuleSpecList()) {
            PsiElement resolvedFile = spec.resolve();
            if (resolvedFile == null || !(resolvedFile instanceof ResFile)) continue;
            ResModuleDecl resolvedModule = ((ResFile) resolvedFile).getEnclosedModule();
            if (resolvedModule == null) continue;
            if (!processModuleLevelEntities((ResFile) resolvedFile, processor, state, false)) return false;
            if (!processSuperModuleParams(resolvedModule, processor, state, true)) return false;
        }
        return true;
    }

    private boolean processSuperModuleParams(@NotNull ResModuleDecl superModule,
                                             @NotNull ResScopeProcessor processor,
                                             @NotNull ResolveState state,
                                             boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResReference.processParameterLikeThings(superModule, delegate);
        return processNamedElements(processor, state, delegate.getVariants(),
                localResolve);
    }

    private boolean processMathSelector(@NotNull ResMathSelectorExp parent,
                                        @NotNull ResScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @Nullable PsiElement another) {
        /*List<ResMathExp> list = parent.getMathExpList();
        if (list.size() > 1 && list.get(1).isEquivalentTo(another)) {
            ResType type = list.get(0).getResType(createContext());
            if (type != null && !processResType(type, processor, state)) return false;
        }*/
        return true;
    }

    private boolean processBuiltin(@NotNull ResScopeProcessor processor,
                                  @NotNull ResolveState state,
                                  @NotNull ResCompositeElement element) {
        ResFile f = ResElementFactory.getHardCodedMathFile(element.getProject());
        return processModuleLevelEntities(f, processor, state, true);
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
        @Override protected boolean crossOff(@NotNull PsiElement e) {
            return false;
        }
    }
}