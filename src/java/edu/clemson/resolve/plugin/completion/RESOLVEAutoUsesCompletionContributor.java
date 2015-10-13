package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiImplUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResReferenceExpressionBase;
import edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil.createPrefixMatcher;
import static edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil.isPrevColonColon;

public class RESOLVEAutoUsesCompletionContributor
        extends
            CompletionContributor {

    public RESOLVEAutoUsesCompletionContributor() {
        extend(CompletionType.BASIC, inRESOLVEFile(),
                new CompletionProvider<CompletionParameters>() {
            @Override protected void addCompletions(
                    @NotNull CompletionParameters parameters,
                    ProcessingContext context,
                    @NotNull CompletionResultSet result) {
                PsiElement position = parameters.getPosition();
                PsiElement parent = position.getParent();
                if(isPrevColonColon(parent)) return;
                PsiFile file = parameters.getOriginalFile();
                if (!(file instanceof ResFile)) return;
                if (!(parent instanceof ResReferenceExpressionBase)) return;
                result = adjustMatcher(parameters, result, parent);

            }

            private CompletionResultSet adjustMatcher(
                    @NotNull CompletionParameters parameters,
                    @NotNull CompletionResultSet result,
                    @NotNull PsiElement parent) {
                int startOffset = parent.getTextRange().getStartOffset();
                String newPrefix = parameters.getEditor().getDocument()
                        .getText(TextRange.create(startOffset,
                                parameters.getOffset()));
                return result.withPrefixMatcher(createPrefixMatcher(newPrefix));
            }
        });
    }

    private static PsiElementPattern.Capture<PsiElement> inRESOLVEFile() {
        return psiElement().inFile(psiElement(ResFile.class));
    }
}
