package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.ResStmt;
import edu.clemson.resolve.plugin.psi.ResUsesItem;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, usesItem(),
                new RESOLVEUsesCompletionProvider());
        extend(CompletionType.BASIC, referenceExpression(),
                new RESOLVEReferenceCompletionProvider());
        extend(CompletionType.BASIC, stmtRefExpression(),
                new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> usesItem() {
        return PlatformPatterns.psiElement()
                .withElementType(ConstEleTypes.ID)
                .withParent(ResUsesItem.class);
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return PlatformPatterns
                .psiElement()
                .withParent(ResReferenceExpressionBase.class);
    }

    /**
     * since I'm awful at writing these patterns, I've split out this pattern
     * to a separate rule extension. Basically this one gets accepted for
     * under construction stmts (e.g.: those without the operator written yet)
     * so we get variable completion for the lhs.
     * @return
     */
    private static PsiElementPattern.Capture<PsiElement> stmtRefExpression() {
        return PlatformPatterns.psiElement()
                .withParent(PlatformPatterns.psiElement(ConstEleTypes.STMT));
    }
}