package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.Key;
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

public class ResMathVarLikeReference
        extends
        PsiPolyVariantReferenceBase<ResMathReferenceExp> {

    private static final Key<SmartPsiElementPointer<ResMathReferenceExp>> CONTEXT = Key.create("CONTEXT");

    ResMathVarLikeReference(@NotNull ResMathReferenceExp o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(), o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(@NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                                               boolean incompleteCode) {
                    return ((ResMathVarLikeReference) psiPolyVariantReferenceBase).resolveInner();
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

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
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
        ResMathReferenceExp qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return ResReference.processQualifierExpression(((ResFile) file), qualifier, processor, state);
        }
        return processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {

        PsiElement parent = myElement.getParent();
        if (parent instanceof ResMathSelectorExp) {
            boolean result = processMathSelector((ResMathSelectorExp) parent, processor, state, myElement);
            if (processor.isCompletion()) return result;
            if (!result || ResPsiImplUtil.prevDot(myElement)) return false;
        }
        PsiElement grandPa = parent.getParent();
        if (grandPa instanceof ResMathSelectorExp && !processMathSelector((ResMathSelectorExp) grandPa, processor, state, parent)) return false;
        if (ResPsiImplUtil.prevDot(parent)) return false;

        if (!processBlock(processor, state, true)) return false;
        if (!processModuleLevelEntities(file, processor, state, localResolve)) return false;
        if (!ResReference.processUsesImports(file, processor, state)) return false;
        if (!ResReference.processSuperModulesUsesImports(file, processor, state)) return false;
        if (!processBuiltin(processor, state, myElement)) return false;
        return true;
    }

    private boolean processBlock(@NotNull ResScopeProcessor processor,
                                 @NotNull ResolveState state,
                                 boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        //process local parameters if we're in a local definition or an operation like thing (doesn't include module
        //params; that's in processModuleLevelEntities)
        ResReference.processBlockParameters(myElement, delegate);
        return processNamedElements(processor, state, delegate.getVariants(), localResolve);
    }

    private boolean processMathSelector(@NotNull ResMathSelectorExp parent,
                                        @NotNull ResScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @Nullable PsiElement another) {
        List<ResMathExp> list = parent.getMathExpList();
        if (list.size() > 1) {
            ResMathExp type = list.get(0).getResMathMetaTypeExp(createContext());
            if (type != null && !processCartProdFields(type, processor, state)) return false;
        }
        return true;
    }

    private boolean processCartProdFields(@NotNull ResMathExp type,
                                          @NotNull ResScopeProcessor processor,
                                          @NotNull ResolveState state) {
        if (type instanceof ResMathReferenceExp) {
            PsiElement resolvedType = ((ResMathReferenceExp) type).getReference().resolve();
            if (resolvedType instanceof ResTypeModelDecl) {
                ResTypeModelDecl asTypeModel = (ResTypeModelDecl) resolvedType;
                if (asTypeModel.getMathExp() != null)
                    type = asTypeModel.getMathExp();
            }
        }
        if (type instanceof ResMathCartProdExp) {
            ResScopeProcessorBase delegate = createDelegate(processor);
            type.processDeclarations(delegate, ResolveState.initial(), null, myElement);
            //List<ResTypeReferenceExp> structRefs = ContainerUtil.newArrayList();
            for (ResMathVarDeclGroup d : ((ResMathCartProdExp) type).getMathVarDeclGroupList()) {
                if (!processNamedElements(processor, state, d.getMathVarDefList(), true))
                    return false;
            }
        }
        return true;
    }

    @NotNull
    private ResolveState createContext() {
        return ResolveState.initial().put(CONTEXT, SmartPointerManager.getInstance(myElement.getProject())
                .createSmartPsiElementPointer(myElement));
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
            if ((e.isUsesClauseVisible() || localResolve) && !processor.execute(e, state)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    private ResMathVarLikeProcessor createDelegate(@NotNull ResScopeProcessor processor) {
        return new ResMathVarLikeProcessor(myElement, processor.isCompletion());
    }

    private static class ResMathVarLikeProcessor extends ResScopeProcessorBase {
        public Map<String, String> implicitlyBoundTypeParameters = new HashMap<>();

        ResMathVarLikeProcessor(@NotNull ResMathReferenceExp origin, boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }

        @Override
        protected boolean crossOff(@NotNull PsiElement e) {
            return false;
        }
    }
}