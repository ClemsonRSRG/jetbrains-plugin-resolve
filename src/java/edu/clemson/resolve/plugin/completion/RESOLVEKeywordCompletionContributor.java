package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.CollectionPattern;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiFilePattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.psi.RFile;

import java.util.Collection;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;
import static com.intellij.patterns.StandardPatterns.*;

public class RESOLVEKeywordCompletionContributor extends CompletionContributor {

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, insideBlockPattern(GoTypes.IDENTIFIER),
                new GoKeywordCompletionProvider(GoCompletionUtil.KEYWORD_PRIORITY, "for", "const", "var", "return", "if", "switch", "go",
                        "defer", "goto"));
    }

    private static PsiElementPattern.Capture<PsiElement> topLevelPattern() {
        return psiElement(RESOLVETokenTypes.)
                .withParent(psiElement(PsiErrorElement.class).withParent(
                        resolveFile()).isFirstAcceptedChild(psiElement()));
    }

    private static PsiFilePattern.Capture<RFile> resolveFile() {
        CollectionPattern<PsiElement> collection = collection(PsiElement.class);
        ElementPattern<Collection<PsiElement>> empty = collection.empty();
        return psiFile(RFile.class).withChildren(collection.filter(
                not(psiElement().whitespaceCommentEmptyOrError()), empty));
    }




    //then global module pattern, etc.

    //operation procedure body pattern
}
