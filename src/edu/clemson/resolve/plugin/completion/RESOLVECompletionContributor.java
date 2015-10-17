package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExpression(),
                new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return PlatformPatterns.psiElement()
                .withParent(ResReferenceExpressionBase.class);
    }

}
