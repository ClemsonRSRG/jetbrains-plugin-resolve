package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.ResMathSymbolName;
import edu.clemson.resolve.jetbrains.psi.ResReferenceExpBase;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.*;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExp(), new RESOLVEReferenceCompletionProvider());
        extend(CompletionType.BASIC, mathReferenceExp(), new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExp() {
        return psiElement().withParent(ResReferenceExpBase.class);
    }

    private static PsiElementPattern.Capture<PsiElement> mathReferenceExp() {
        return psiElement().withParent(psiElement(ResMathSymbolName.class).withParent(ResReferenceExpBase.class));
    }

    public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
        boolean result = typeChar == ':' && position.getNode().getElementType() == ResTypes.COLON;
        return result;
    }
}
