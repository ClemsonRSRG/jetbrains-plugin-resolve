package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.text.StringUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResTypeLikeNodeDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int NOT_IMPORTED_TYPE_PRIORITY = 5;
    public static final int TYPE_PRIORITY = NOT_IMPORTED_TYPE_PRIORITY + 10;

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull PrefixMatcher original) {
        return createPrefixMatcher(original.getPrefix());
    }

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull String prefix) {
        return new CamelHumpMatcher(prefix, false);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResTypeLikeNodeDecl t) {
        return createTypeLookupElement(t, StringUtil.notNullize(
                t.getName()), null, null, TYPE_PRIORITY);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResTypeLikeNodeDecl t, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler,
            @Nullable String importPath, double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                .withInsertHandler(handler).withIcon(RESOLVEIcons.TYPE_REPR);
        if (importPath != null) builder =
                builder.withTailText(" " + importPath, true);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }
}
