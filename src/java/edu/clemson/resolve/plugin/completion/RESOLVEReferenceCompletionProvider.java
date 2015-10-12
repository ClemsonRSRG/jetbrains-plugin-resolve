package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import edu.clemson.resolve.plugin.psi.impl.ResTypeReference;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResScopeProcessor;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEReferenceCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    @Override protected void addCompletions(
            @NotNull CompletionParameters completionParameters,
            ProcessingContext processingContext,
            @NotNull CompletionResultSet set) {
        ResReferenceExpressionBase expression =
                PsiTreeUtil.getParentOfType(completionParameters.getPosition(),
                        ResReferenceExpressionBase.class);
        if (expression != null) {
            fillVariantsByReference(expression.getReference(),
                    set.withPrefixMatcher(
                            createPrefixMatcher(set.getPrefixMatcher())));
        }
    }

    private static void fillVariantsByReference(
            @Nullable PsiReference reference,
            @NotNull CompletionResultSet result) {
        if (reference == null) return;
        else if (reference instanceof ResReference) {
            ((ResReference)reference).processResolveVariants(new MyRESOLVEScopeProcessor(result, false));
            PsiElement element = reference.getElement();
        }
        if (reference instanceof ResTypeReference) {
            PsiElement element = reference.getElement();
           // final PsiElement spec = PsiTreeUtil.getParentOfType(element, GoFieldDeclaration.class, GoTypeSpec.class);
            //final boolean insideParameter = PsiTreeUtil.getParentOfType(element, GoParameterDeclaration.class) != null;

            ResScopeProcessor aProcessor = new MyRESOLVEScopeProcessor(result, true) {
                @Override
                protected boolean accept(@NotNull PsiElement e) {
                    return true;//e != spec &&
                    // !(insideParameter &&
                    //         (e instanceof GoNamedSignatureOwner || e instanceof GoVarDefinition || e instanceof GoConstDefinition));
                }
            };
            ((ResTypeReference) reference).processResolveVariants(aProcessor);
        }
    }

    private static void addElement(@NotNull PsiElement o, @NotNull ResolveState state, boolean forTypes, @NotNull CompletionResultSet set) {
        LookupElement lookup = createLookupElement(o, state, forTypes);
        if (lookup != null) {
            set.addElement(lookup);
        }
    }

    @Nullable private static LookupElement createLookupElement(
            @NotNull PsiElement o, @NotNull ResolveState state,
            boolean forTypes) {
        if (o instanceof ResNamedElement) {
            if (o instanceof ResAbstractTypeDecl) {
                return RESOLVECompletionUtil.createTypeLookupElement((ResAbstractTypeDecl)o);
                       // : GoCompletionUtil.createTypeConversionLookupElement((GoTypeSpec)o);
            }
            else {
                return RESOLVECompletionUtil.createVariableLikeLookupElement((ResNamedElement) o);
            }
        }

        return null;
    }

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull PrefixMatcher original) {
        return createPrefixMatcher(original.getPrefix());
    }

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull String prefix) {
        return new CamelHumpMatcher(prefix, false);
    }

    public static class MyRESOLVEScopeProcessor extends ResScopeProcessor {
        private final String myDanKey = "";
        private final CompletionResultSet myResult;
        private final boolean myForTypes;

        public MyRESOLVEScopeProcessor(@NotNull CompletionResultSet result,
                                       boolean forTypes) {
            myResult = result;
            myForTypes = forTypes;
        }

        @Override public boolean execute(@NotNull PsiElement o,
                                         @NotNull ResolveState state) {
            if (accept(o)) {
                addElement(o, state, myForTypes, myResult);
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