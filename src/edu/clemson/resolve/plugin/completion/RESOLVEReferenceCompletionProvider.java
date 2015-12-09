package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.ResMathVarLikeReference;
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
        ResReferenceExpBase expression = PsiTreeUtil.getParentOfType(
                parameters.getPosition(), ResReferenceExpBase.class);
        if (expression != null) {
            fillVariantsByReference(expression.getReference(),
                    set.withPrefixMatcher(createPrefixMatcher(set.getPrefixMatcher())));
        }
        PsiElement parent = parameters.getPosition().getParent();
        if (parent != null) {
            fillVariantsByReference(parent.getReference(),
                    set.withPrefixMatcher(createPrefixMatcher(set.getPrefixMatcher())));
        }
    }

    private static void fillVariantsByReference(
            @Nullable PsiReference reference,
            @NotNull CompletionResultSet result) {
        if (reference == null) return;
        /*if (reference instanceof PsiMultiReference) {
            PsiReference[] references = ((PsiMultiReference)reference).getReferences();
            ContainerUtil.sort(references, PsiMultiReference.COMPARATOR);
            fillVariantsByReference(ArrayUtil.getFirstElement(references), result);
        }*/
        else if (reference instanceof ResReference) {
            ((ResReference)reference).processResolveVariants(
                    new MyRESOLVEScopeProcessor(result, false));
        }
        else if (reference instanceof ResTypeReference) {
            PsiElement element = reference.getElement();
            ResScopeProcessor aProcessor = new MyRESOLVEScopeProcessor(result, true) {
                @Override
                protected boolean accept(@NotNull PsiElement e) {
                    return e instanceof ResTypeLikeNodeDecl ||
                           e instanceof ResFacilityDecl ||
                           e instanceof ResTypeParamDecl;
                }
            };
            ((ResTypeReference) reference).processResolveVariants(aProcessor);
        }
        else if (reference instanceof ResMathVarLikeReference) {
            PsiElement element = reference.getElement();
            ResScopeProcessor aProcessor = new MyRESOLVEScopeProcessor(result, true) {
                @Override protected boolean accept(@NotNull PsiElement e) {
                    return e instanceof ResMathDefinitionSignature ||
                            e instanceof ResMathVarDef ||
                            e instanceof ResParamDef ||
                            e instanceof ResTypeParamDecl;
                }
            };
            ((ResMathVarLikeReference) reference).processResolveVariants(aProcessor);
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
            if (o instanceof ResMathDefinitionSignature) {
                String name = ((ResMathDefinitionSignature)o).getName();
                if (name != null) {
                    return RESOLVECompletionUtil
                            .createDefinitionLookupElement(
                                    (ResMathDefinitionSignature) o, name, null,
                                    RESOLVECompletionUtil.DEFINITION_PRIORITY);
                }
            }
            else if (o instanceof ResTypeLikeNodeDecl ||
                     o instanceof ResTypeParamDecl) {
                return RESOLVECompletionUtil
                        .createTypeLookupElement((ResNamedElement)o);
            }
            else if (o instanceof ResFacilityDecl) {
                return RESOLVECompletionUtil
                        .createFacilityLookupElement(((ResFacilityDecl) o));
            }
            else {
                //TODO: Apply type info to the lookup renderers for these 'var like' elements
                return RESOLVECompletionUtil
                        .createVariableLikeLookupElement((ResNamedElement) o);
            }
        /*
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