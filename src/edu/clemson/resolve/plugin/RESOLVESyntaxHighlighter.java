package edu.clemson.resolve.plugin;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.psi.RESOLVETypes;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RESOLVESyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey STRING =
            createTextAttributesKey("STRING",
                    DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("KEYWORD",
                    DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("LINE_COMMENT",
                    DefaultLanguageHighlighterColors.LINE_COMMENT);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{ HighlighterColors.BAD_CHARACTER };
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new _RESOLVELexer((Reader) null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (RESOLVETokenKeywords.KEYWORDS.contains(tokenType)) {
            return new TextAttributesKey[]{KEYWORD};
        } else if (tokenType.equals(RESOLVETypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
