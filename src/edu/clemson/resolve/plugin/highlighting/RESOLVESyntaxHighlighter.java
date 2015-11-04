package edu.clemson.resolve.plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVEParserDefinition;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.lexer.ResolveLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static edu.clemson.resolve.plugin.highlighting.RESOLVESyntaxHighlightingColors.*;

public class RESOLVESyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES =
            new HashMap<IElementType, TextAttributesKey>();

    static {
        fillMap(ATTRIBUTES, LINE_COMMENT, RESOLVEParserDefinition.LINE_COMMENT);
        fillMap(ATTRIBUTES, BLOCK_COMMENT, RESOLVEParserDefinition.MULTILINE_COMMENT);
        fillMap(ATTRIBUTES, PARENTHESES, ResTypes.LPAREN, ResTypes.RPAREN);
        fillMap(ATTRIBUTES, BRACES, ResTypes.LBRACE, ResTypes.RBRACE, ResTypes.DBL_LBRACE, ResTypes.DBL_RBRACE);
        fillMap(ATTRIBUTES, BRACKETS, ResTypes.LBRACK, ResTypes.RBRACK);
        fillMap(ATTRIBUTES, BAD_CHARACTER, TokenType.BAD_CHARACTER);
        fillMap(ATTRIBUTES, IDENTIFIER, ResTypes.IDENTIFIER);
        fillMap(ATTRIBUTES, DOT, ResTypes.DOT, ResTypes.DOT_DOT, ResTypes.TRIPLE_DOT);
        fillMap(ATTRIBUTES, COLON, ResTypes.COLON);
        fillMap(ATTRIBUTES, SEMICOLON, ResTypes.SEMICOLON);
        fillMap(ATTRIBUTES, COMMA, ResTypes.COMMA);
        fillMap(ATTRIBUTES, RESOLVEParserDefinition.OPERATORS, OPERATOR);
        fillMap(ATTRIBUTES, RESOLVEParserDefinition.KEYWORDS, KEYWORD);
        fillMap(ATTRIBUTES, RESOLVEParserDefinition.NUMBERS, NUMBER);
        fillMap(ATTRIBUTES, RESOLVEParserDefinition.STRING_LITERALS, STRING);
    }

    @NotNull @Override public Lexer getHighlightingLexer() {
        return new ResolveLexer();
    }

    @NotNull public TextAttributesKey[] getTokenHighlights(
            IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }
}
