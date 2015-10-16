package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesSpec usesSpec) {
        String text = usesSpec.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
                TextRange.EMPTY_RANGE;    }
}
