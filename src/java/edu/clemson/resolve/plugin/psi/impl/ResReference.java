package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResRefExpBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResReference extends PsiPolyVariantReferenceBase<ResRefExpBase> {
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

    public ResReference(@NotNull ResRefExpBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    @NotNull private ResolveResult[] resolveInner() {
        String identifierText = getName();
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(
                identifierText, result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull static ResScopeProcessor createResolveProcessor(
            @NotNull final String text,
            @NotNull final Collection<ResolveResult> result,
            @NotNull final ResCompositeElement o) {
        return new ResScopeProcessor() {

            @Override public boolean execute(@NotNull PsiElement element,
                                             @NotNull ResolveState state) {
                if (element.equals(o)) {
                    return !result.add(new PsiElementResolveResult(element));
                }
                String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME),
                        element instanceof PsiNamedElement ?
                                ((PsiNamedElement) element).getName() : null);
                if (text.equals(name)) {
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
        ResRefExpBase qualifier = myElement.getQualifier();
        return //qualifier != null
               // ? processQualifierExpression(((ResFile)file), qualifier, processor, state)
                //:
        processUnqualifiedResolve(((ResFile)file), processor, state, true);
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createVarDelegate(processor);

        return true;
    }

    @NotNull private ResVarProcessor createVarDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResVarProcessor(getName(), myElement, processor.isCompletion(), true) {
            @Override
            protected boolean condition(@NotNull PsiElement e) {
                return super.condition(e);
            }
        };
    }

    private String getName() {
        return myElement.getIdentifier().getText();
    }
}
