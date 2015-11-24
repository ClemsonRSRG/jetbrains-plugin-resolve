package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.ResRefExp;
import edu.clemson.resolve.plugin.psi.ResRefExpBase;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEReferenceCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    @Override protected void addCompletions(
            @NotNull CompletionParameters parameters,
            ProcessingContext context,
            @NotNull CompletionResultSet set) {
        ResRefExpBase expression = PsiTreeUtil.getParentOfType(
                parameters.getPosition(), ResRefExpBase.class);
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
        } else if ..*/
        if (reference instanceof ResReference) {
            ((ResReference)reference).processResolveVariants(new MyResScopeProcessor(result, false));

            PsiElement element = reference.getElement();
            if (element instanceof ResRefExp && PsiTreeUtil.getParentOfType(element, ResCompositeLit.class) != null) {
                new ResFieldNameReference(((ResRefExp)element)).processResolveVariants(new MyResScopeProcessor(result, false));
            }
        }
        else if (reference instanceof ResTypeReference) {
            PsiElement element = reference.getElement();
            final PsiElement spec = PsiTreeUtil.getParentOfType(element, ResFieldDeclaration.class, ResTypeSpec.class);
            final boolean insideParameter = PsiTreeUtil.getParentOfType(element, ResParameterDeclaration.class) != null;
            ((ResTypeReference)reference).processResolveVariants(new MyResScopeProcessor(result, true) {
                @Override
                protected boolean accept(@NotNull PsiElement e) {
                    return e != spec &&
                            !(insideParameter &&
                                    (e instanceof ResNamedSignatureOwner || e instanceof ResVarDefinition || e instanceof ResConstDefinition));
                }
            });
            if (element instanceof ResReferenceExpressionBase && element.getParent() instanceof ResReceiverType) {
                fillVariantsByReference(new ResReference((ResReferenceExpressionBase)element), result);
            }
        }
        else if (reference instanceof ResCachedReference) {
            ((ResCachedReference)reference).processResolveVariants(new MyResScopeProcessor(result, false));
        }
    }
}
