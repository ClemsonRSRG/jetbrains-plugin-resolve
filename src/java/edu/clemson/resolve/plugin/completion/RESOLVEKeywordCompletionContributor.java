package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

public class RESOLVEKeywordCompletionContributor extends CompletionContributor {

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, insideBlockPattern(GoTypes.IDENTIFIER),
                new GoKeywordCompletionProvider(GoCompletionUtil.KEYWORD_PRIORITY, "for", "const", "var", "return", "if", "switch", "go",
                        "defer", "goto"));
    }
}
