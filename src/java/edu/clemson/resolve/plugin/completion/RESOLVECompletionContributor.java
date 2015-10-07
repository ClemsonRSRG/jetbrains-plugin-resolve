package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResUsesItem;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, usesItem(), new RESOLVEUsesCompletionProvider());
        //extend(CompletionType.BASIC, referenceExpression(), new GoReferenceCompletionProvider());
        //extend(CompletionType.BASIC, goReference(), new GoReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> usesItem() {
        return PlatformPatterns.psiElement()
                .withElementType(ConstTokenTypes.ID)
                .withParent(ResUsesItem.class);
    }
}
