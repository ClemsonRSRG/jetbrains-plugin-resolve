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
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import edu.clemson.resolve.plugin.psi.impl.ResCachedReference;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RESOLVECompletionContributor extends CompletionContributor {

    public RESOLVECompletionContributor() {
        extend(CompletionType.BASIC, referenceExpression(),
                new RESOLVEReferenceCompletionProvider());
        extend(CompletionType.BASIC, resReference(),
                new RESOLVEReferenceCompletionProvider());
    }

    private static PsiElementPattern.Capture<PsiElement> referenceExpression() {
        return PlatformPatterns.psiElement()
                .withParent(ResReferenceExpressionBase.class);
    }

    private static PsiElementPattern.Capture<PsiElement> resReference() {
        return PlatformPatterns.psiElement()
                .withParent(PlatformPatterns.psiElement()
                        .withReference(ResCachedReference.class));
    }
}
