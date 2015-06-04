package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleElementType;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

import java.util.Arrays;
import java.util.List;

public class RESOLVETokenTypes {

    public static final List<TokenElementType> TOKEN_ELEMENT_TYPES =
            ElementTypeFactory.getTokenElementTypes(RESOLVELanguage.INSTANCE,
                    Arrays.asList(Resolve.tokenNames));
    public static final List<RuleElementType> RULE_ELEMENT_TYPES =
            ElementTypeFactory.getRuleElementTypes(RESOLVELanguage.INSTANCE,
                    Arrays.asList(Resolve.ruleNames));

    public static final TokenSet COMMENTS =
            ElementTypeFactory.createTokenSet(
                    RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.DOC_COMMENT,
                    ResolveLexer.LINE_COMMENT);

    public static final TokenSet WHITESPACES =
            ElementTypeFactory.createTokenSet(
                    RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.WS);

    public static final TokenSet KEYWORDS =
            ElementTypeFactory.createTokenSet(
                    RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.PRECIS,ResolveLexer.END);
}
