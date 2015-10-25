package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.text.StringUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFacilityDecl;
import edu.clemson.resolve.plugin.psi.ResTypeLikeNodeDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;
    public static final int TYPE_PRIORITY = 10;
    public static final int FACILITY_PRIORITY = 5;

    private RESOLVECompletionUtil() {}

    private static class Lazy {
        private static final SingleCharInsertHandler FACILITY_INSERT_HANDLER =
                new SingleCharInsertHandler('.');
    }

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
                t.getName()), null, TYPE_PRIORITY);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResTypeLikeNodeDecl t, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler, double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                        .withInsertHandler(handler).withIcon(RESOLVEIcons.FILE);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @Nullable public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility) {
        return createFacilityLookupElement(facility,
                facility.getIdentifier().getText());
    }

    @Nullable public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility, @NotNull String name) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(name)
                        .withInsertHandler(Lazy.FACILITY_INSERT_HANDLER)
                        .withIcon(RESOLVEIcons.FACILITY), FACILITY_PRIORITY);
    }
}
