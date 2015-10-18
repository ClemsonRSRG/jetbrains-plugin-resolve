package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResModule;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.StandardPatterns.or;
import static com.intellij.patterns.StandardPatterns.string;

import static edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil.KEYWORD_PRIORITY;

public class RESOLVEKeywordCompletionContributor
        extends
            CompletionContributor implements DumbAware {

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, usesPattern(),
                new RESOLVEKeywordCompletionProvider(KEYWORD_PRIORITY, "uses"));
    }

    private static PsiElementPattern.Capture<PsiElement> usesPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class));
    }

}
