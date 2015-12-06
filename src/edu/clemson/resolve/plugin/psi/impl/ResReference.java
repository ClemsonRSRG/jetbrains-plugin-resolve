package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpBase> {

    public static final Key<String > ACTUAL_NAME = Key.create("ACTUAL_NAME");

    private static final ResolveCache
            .PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResReference)psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    public ResReference(@NotNull ResReferenceExpBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    @NotNull private ResolveResult[] resolveInner() {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull static ResScopeProcessor createResolveProcessor(
            @NotNull final Collection<ResolveResult> result,
            @NotNull final ResReferenceExpBase o) {
        return new ResScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element,
                                   @NotNull ResolveState state) {
                if (element.equals(o)) {
                    return !result.add(
                            new PsiElementResolveResult(element));
                }
                String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME),
                        element instanceof PsiNamedElement ?
                                ((PsiNamedElement)element).getName() : null);
                if (name != null && o.getIdentifier().textMatches(name)) {
                    result.add(new PsiElementResolveResult(element));
                    return false;
                }
                return true;
            }
        };
    }

    @NotNull @Override public ResolveResult[] multiResolve(
            boolean incompleteCode) {
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
        ResReferenceExpBase qualifier = myElement.getQualifier();
        return //qualifier != null
                // ? processQualifierExpression(((ResFile)file), qualifier, processor, state)
                //:
                processUnqualifiedResolve(((ResFile)file), processor, state, true);
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        //ResScopeProcessorBase delegate = createVarDelegate(processor);

        return true;
    }

    /*@NotNull private ResVarProcessor createVarDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResVarProcessor(getName(), myElement, processor.isCompletion(), true) {
            @Override
            protected boolean crossOff(@NotNull PsiElement e) {
                return super.crossOff(e);
            }
        };
    }*/

    static boolean processUsesRequests(@NotNull ResFile file,
                                       @NotNull ResScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       @NotNull ResCompositeElement element,
                                       boolean searchImplicitUses) {
        for (ResUsesItem u : file.getUsesItems()) {
            //this file resolve is failing for whatever reason when we're trying to add completions... is this a concurrency thing maybe?
            //works the rest of the time...
            PsiElement resolvedModule = u.getModuleSpec().resolve();
            if (resolvedModule == null || !(resolvedModule instanceof ResFile)) continue;
            if (!processModuleLevelEntities((ResFile) resolvedModule, processor, state, false)) return false;
        }
        ResModuleDecl module = file.getEnclosedModule();
        if (module != null && searchImplicitUses) {
            //Now process module decl implicit imports
            for (ResModuleSpec moduleSpec : module.getModuleSpecList()) {
                PsiElement resolvedModule = moduleSpec.resolve();
                if (resolvedModule == null || !(resolvedModule instanceof ResFile)) continue;
                if (!processModuleLevelEntities((ResFile) resolvedModule, processor, state, false)) return false;
            }
        }
        return true;
    }

    protected static boolean processModuleLevelEntities(@NotNull ResFile file,
                                                        @NotNull ResScopeProcessor processor,
                                                        @NotNull ResolveState state,
                                                        boolean localProcessing) {
        //if (!processNamedElements(processor, state, file.getConstants(), localProcessing)) return false;
        //if (!processNamedElements(processor, state, file.getVars(), localProcessing)) return false;
       /* if (!processNamedElements(processor, state, file.getOperationImpls(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getOperationDecls(), localProcessing)) return false;*/
        if (!processNamedElements(processor, state, file.getFacilities(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getTypes(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getMathDefinitionSignatures(), localProcessing)) return false;
        return true;
    }

    static boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @NotNull Collection<? extends ResNamedElement> elements,
                                        boolean localResolve) {
        return processNamedElements(processor, state, elements, localResolve, false);
    }

    static boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @NotNull Collection<? extends ResNamedElement> elements,
                                        boolean localResolve,
                                        boolean checkContainingFile) {

        for (ResNamedElement definition : elements) {
            //if (!definition.isValid() || checkContainingFile && !allowed(definition.getContainingFile(), contextFile)) continue;
            if ((localResolve || definition.isPublic()) &&
                    !processor.execute(definition, state)) return false;
        }
        return true;
    }

    private String getName() {
        return myElement.getIdentifier().getText();
    }
}