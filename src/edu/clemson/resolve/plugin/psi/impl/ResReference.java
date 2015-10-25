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
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpressionBase> {

    public static final Key<String> ACTUAL_NAME = Key.create("ACTUAL_NAME");
    public static final Key<SmartPsiElementPointer<ResReferenceExpressionBase>> CONTEXT = Key.create("CONTEXT");

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResReference) psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    public ResReference(@NotNull ResReferenceExpressionBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = createContext();
        ResReferenceExpressionBase qualifier = myElement.getQualifier();
        return //qualifier != null
                // ? processQualifierExpression(((GoFile)file), qualifier, processor, state)
                //:
                processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }

    @NotNull public ResolveState createContext() {
        return ResolveState.initial().put(CONTEXT,
                SmartPointerManager.getInstance(myElement.getProject())
                        .createSmartPsiElementPointer(myElement));
    }

    @NotNull static ResScopeProcessor createResolveProcessor(
            @NotNull final Collection<ResolveResult> result,
            @NotNull final ResReferenceExpressionBase o) {
        return new ResScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element,
                                   @NotNull ResolveState state) {
                if (element.equals(o))
                    return !result.add(new PsiElementResolveResult(element));

                String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME),
                        element instanceof PsiNamedElement ?
                                ((PsiNamedElement) element).getName() : null);
                String oIdentifierText = o.getIdentifier().getText();
                if (name != null && o.getIdentifier().textMatches(name)) {
                    result.add(new PsiElementResolveResult(element));
                    return false;
                }
                return true;
            }
        };
    }

    @Override @NotNull public ResolveResult[] multiResolve(
            boolean incompleteCode) {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        return ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    static boolean processUsesRequests(@NotNull ResFile file,
                                       @NotNull ResScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       @NotNull ResCompositeElement element) {
        for (ResUsesSpec u : file.getUsesSpecs()) {
            //this file resolve is failing for whatever reason when we're trying to add completions... is this a concurrency thing maybe?
            //works the rest of the time...
            PsiFile resolvedFile = u.resolve();
            if (resolvedFile == null || !(resolvedFile instanceof ResFile)) continue;
            //if (!processor.execute(((ResFile) resolvedFile).getEnclosedModule(), state.put(ACTUAL_NAME, u.getText()))) return true;
            if (!processFileEntities((ResFile)resolvedFile, processor, state, false)) return false;
        }
        return true;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        /*ResScopeProcessorBase delegate = createDelegate(processor);
        ResolutionUtil.treeWalkUp(myElement, delegate);
        //       if (processUsesRequests(file, processor, state, myElement)) return false;

        if (!processNamedElements(processor, state, delegate.getVariants(), localResolve)) return false;
*/
        return true;
    }

    protected static boolean processFileEntities(@NotNull ResFile file,
                                                 @NotNull ResScopeProcessor processor,
                                                 @NotNull ResolveState state,
                                                 boolean localProcessing) {
        //if (!processNamedElements(processor, state, file.getConstants(), localProcessing)) return false;
        //if (!processNamedElements(processor, state, file.getVars(), localProcessing)) return false;
        //if (!processNamedElements(processor, state, file.getFunctions(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getFacilities(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getTypes(), localProcessing)) return false;
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

    @Nullable private static PsiFile getContextFile(
            @NotNull ResolveState state) {
        SmartPsiElementPointer<ResReferenceExpressionBase> context =
                state.get(CONTEXT);
        return context != null ? context.getContainingFile() : null;
    }
}
