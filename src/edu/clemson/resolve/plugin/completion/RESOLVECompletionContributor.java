package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResMathNameIdentifierSymbol;
import edu.clemson.resolve.plugin.psi.ResReferenceExpBase;
import edu.clemson.resolve.plugin.psi.impl.ResCachedReference;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.intellij.patterns.PlatformPatterns.*;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExp(),
                new RESOLVEReferenceCompletionProvider());
        extend(CompletionType.BASIC, mathReferenceExp(),
                new RESOLVEReferenceCompletionProvider());
        //extend(CompletionType.BASIC, resReference(),
        //        new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExp() {
        return psiElement().withParent(ResReferenceExpBase.class);
    }

    private static PsiElementPattern.Capture<PsiElement> mathReferenceExp() {
        return psiElement().withParent(psiElement(ResMathNameIdentifierSymbol.class)
                .withParent(ResReferenceExpBase.class));
    }


    // private static PsiElementPattern.Capture<PsiElement> resReference() {
   //     return psiElement().withParent(psiElement()
   //             .withReference(ResCachedReference.class));
   // }

    /**
     * Allow autoPopup to appear after custom symbol
     */
   // public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
   //     return typeChar != ';' && typeChar != ' ' && typeChar != ')';
   // }
}
