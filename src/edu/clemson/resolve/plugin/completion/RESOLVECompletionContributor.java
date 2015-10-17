package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.ResTypeRefNode;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, usesSpec(),
                new RESOLVEUsesCompletionProvider());
        extend(CompletionType.BASIC, referenceExpression(), new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> usesSpec() {
        return PlatformPatterns.psiElement()
                .withElementType(ResTypes.IDENTIFIER)
                .withParent(ResUsesSpec.class);
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return PlatformPatterns.psiElement().withParent(ResTypeRefNode.class); }


}
