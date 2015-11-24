package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import org.jetbrains.annotations.NotNull;

public class RESOLVECompletionUtil {

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull PrefixMatcher original) {
        return createPrefixMatcher(original.getPrefix());
    }

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull String prefix) {
        return new CamelHumpMatcher(prefix, false);
    }
}
