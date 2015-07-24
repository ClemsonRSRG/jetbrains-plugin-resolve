package edu.clemson.resolve.plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.RESOLVEParserDefinition;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.adaptors.RESOLVELexerAdaptor;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static edu.clemson.resolve.plugin.highlighting.RESOLVESyntaxHighlightingColors.*;

public class RESOLVESyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

    static {
        fillMap(ATTRIBUTES, RESOLVETokenTypes.COMMENTS, COMMENT);
        fillMap(ATTRIBUTES, RESOLVETokenTypes.KEYWORDS, KEYWORD);
        fillMap(ATTRIBUTES, BAD_CHARACTER, TokenType.BAD_CHARACTER);
    }

    /*
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey JAVADOC_COMMENT =
            createTextAttributesKey("JAVADOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{HighlighterColors.BAD_CHARACTER};
    //private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT, JAVADOC_COMMENT, BLOCK_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
    */
    @NotNull @Override public Lexer getHighlightingLexer() {
        ResolveLexer lexer = new ResolveLexer(null);
        return new RESOLVELexerAdaptor(RESOLVELanguage.INSTANCE, lexer);
    }

    @NotNull @Override public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {

        return pack(ATTRIBUTES.get(tokenType));

        /*if ( RESOLVETokenTypes.KEYWORDS.contains(tokenType) ){
            return new TextAttributesKey[]{KEYWORD};
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DOC_COMMENT)) {
            return COMMENT_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT)) {
            return COMMENT_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.BAD_TOKEN_TYPE) {
            return BAD_CHAR_KEYS;
        }
        else {
            return EMPTY_KEYS;
        }*/
    }
}
