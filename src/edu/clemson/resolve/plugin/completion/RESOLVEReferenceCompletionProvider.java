package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import edu.clemson.resolve.plugin.psi.impl.ResScopeProcessor;
import edu.clemson.resolve.plugin.psi.impl.ResTypeReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil.createPrefixMatcher;

public class RESOLVEReferenceCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    @Override protected void addCompletions(
            @NotNull CompletionParameters parameters,
            ProcessingContext context,
            @NotNull CompletionResultSet set) {
        ResReferenceExpressionBase expression =
                PsiTreeUtil.getParentOfType(parameters.getPosition(),
                        ResReferenceExpressionBase.class);
        if (expression != null) {
            fillVariantsByReference(expression.getReference(),
                    set.withPrefixMatcher(
                            createPrefixMatcher(set.getPrefixMatcher())));
        }
        PsiElement parent = parameters.getPosition().getParent();
        if (parent != null) {
            fillVariantsByReference(parent.getReference(),
                    set.withPrefixMatcher(
                            createPrefixMatcher(set.getPrefixMatcher())));
        }
    }

    private static void fillVariantsByReference(
            @Nullable PsiReference reference,
            @NotNull CompletionResultSet result) {
        if (reference == null) return;
        else if (reference instanceof ResReference) {
            ((ResReference)reference).processResolveVariants(
                    new MyRESOLVEScopeProcessor(result, false));
        }
        if (reference instanceof ResTypeReference) {
            /*PsiElement element = reference.getElement();
            ResScopeProcessor aProcessor = new MyRESOLVEScopeProcessor(result, true) {
                @Override
                protected boolean accept(@NotNull PsiElement e) {
                    return e instanceof ResTypeLikeNodeDecl || e instanceof ResFacilityDecl;//e != spec &&
                    // !(insideParameter &&
                    //         (e instanceof ResNamedSignatureOwner || e instanceof ResVarDef));
                }
            };
            ((ResTypeReference) reference).processResolveVariants(aProcessor);*/
        }
    }

    private static void addElement(@NotNull PsiElement o,
                                   @NotNull ResolveState state,
                                   boolean forTypes,
                                   @NotNull CompletionResultSet set) {
        LookupElement lookup = createLookupElement(o, state, forTypes);
        if (lookup != null) {
            set.addElement(lookup);
        }
    }

    @Nullable private static LookupElement createLookupElement(
            @NotNull PsiElement o, @NotNull ResolveState state,
            boolean forTypes) {
        if (o instanceof ResNamedElement) {
            /*if (o instanceof ResTypeLikeNodeDecl) {
                return RESOLVECompletionUtil
                        .createTypeLookupElement((ResTypeLikeNodeDecl) o);
            }
            else if (o instanceof ResFacilityDecl) {
                return RESOLVECompletionUtil
                        .createFacilityLookupElement(((ResFacilityDecl) o));
            }
            else if (o instanceof ResNamedSignatureOwner &&
                    ((ResNamedSignatureOwner)o).getName() != null) {
                String name = ((ResNamedSignatureOwner)o).getName();
                if (name != null) {
                    return RESOLVECompletionUtil
                            .createFunctionOrMethodLookupElement(
                                    (ResNamedSignatureOwner) o, name, null,
                                    RESOLVECompletionUtil.FUNCTION_PRIORITY);
                }
            }
            else {
                return RESOLVECompletionUtil
                        .createVariableLikeLookupElement((ResNamedElement) o);
            }*/
        }
        return null;
    }

    public static class MyRESOLVEScopeProcessor extends ResScopeProcessor {
        private final CompletionResultSet result;
        private final boolean forTypes;

        public MyRESOLVEScopeProcessor(@NotNull CompletionResultSet result,
                                       boolean forTypes) {
            this.result = result;
            this.forTypes = forTypes;
        }

        @Override public boolean execute(@NotNull PsiElement o,
                                         @NotNull ResolveState state) {
            if (accept(o)) {
                addElement(o, state, forTypes, result);
            }
            return true;
        }

        protected boolean accept(@NotNull PsiElement e) {
            return true;
        }

        @Override public boolean isCompletion() {
            return true;
        }
    }

}
