package edu.clemson.resolve.plugin;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.Key;
import gnu.trove.TObjectIntHashMap;
import org.jetbrains.annotations.NotNull;

public class RESOLVEParserUtil extends GeneratedParserUtilBase {
    private static final Key<TObjectIntHashMap<String>> MODES_KEY =
            Key.create("MODES_KEY");

    @NotNull private static TObjectIntHashMap<String> getParsingModes(
            @NotNull PsiBuilder builder_) {
        TObjectIntHashMap<String> flags =
                builder_.getUserDataUnprotected(MODES_KEY);
        if (flags == null) builder_.putUserDataUnprotected(
                MODES_KEY, flags = new TObjectIntHashMap<String>());
        return flags;
    }

    public static boolean withOn(PsiBuilder builder_, int level_,
                                 String mode, Parser parser) {
        return withImpl(builder_, level_, mode, true, parser, parser);
    }

    private static boolean withImpl(PsiBuilder builder_, int level_,
                                    String mode, boolean onOff, Parser whenOn,
                                    Parser whenOff) {
        TObjectIntHashMap<String> map = getParsingModes(builder_);
        int prev = map.get(mode);
        boolean change = ((prev & 1) == 0) == onOff;
        if (change) map.put(mode, prev << 1 | (onOff ? 1 : 0));
        boolean result = (change ? whenOn : whenOff).parse(builder_, level_);
        if (change) map.put(mode, prev);
        return result;
    }
}
