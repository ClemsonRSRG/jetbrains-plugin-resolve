package edu.clemson.resolve.plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.lexer.ResolveLexer;
import org.jetbrains.annotations.NotNull;

public class RESOLVESyntaxHighlighter extends SyntaxHighlighterBase {
    @NotNull @Override public Lexer getHighlightingLexer() {
        return new ResolveLexer();
    }

    @NotNull @Override public TextAttributesKey[] getTokenHighlights(
            IElementType iElementType) {
        return new TextAttributesKey[0];
    }
}
