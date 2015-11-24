package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.ResRefExpBase;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExpression(),
                new RESOLVEReferenceCompletionProvider());
         /*extend(CompletionType.BASIC, resReference(),
                new RESOLVEReferenceCompletionProvider());
        extend(CompletionType.BASIC, usesReference(),
                new RESOLVEUsesCompletionProvider());*/
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return psiElement().withParent(ResRefExpBase.class);
    }

    /*private static PsiElementPattern.Capture<PsiElement> resReference() {
        return psiElement().withParent(psiElement()
                .withReference(ResCachedReference.class));
    }

    private static PsiElementPattern.Capture<PsiElement> usesReference() {
        return PlatformPatterns.psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.USES_SPEC));
    }*/

    /**
     * Allow autoPopup to appear after custom symbol
     */
   // public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
   //     return typeChar != ';';
   // }
}
