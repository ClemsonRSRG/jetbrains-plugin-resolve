package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlighter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by daniel on 6/4/16.
 */
public class VCDisplayData {

    @NotNull
    SyntaxHighlighter getHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    @NonNls
    @NotNull
    String getText() {
        return "Max_Length = 0";
    }
}
