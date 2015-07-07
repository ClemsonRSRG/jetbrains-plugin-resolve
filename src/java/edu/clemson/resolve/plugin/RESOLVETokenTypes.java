package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleElementType;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

import java.util.Arrays;
import java.util.List;

public class RESOLVETokenTypes {

    public static IElementType BAD_TOKEN_TYPE = new IElementType("BAD_TOKEN",
            RESOLVELanguage.INSTANCE);

    public static final List<TokenElementType> TOKEN_ELEMENT_TYPES =
            ElementTypeFactory.getTokenElementTypes(RESOLVELanguage.INSTANCE,
                    Arrays.asList(Resolve.tokenNames));
    public static final List<RuleElementType> RULE_ELEMENT_TYPES =
            ElementTypeFactory.getRuleElementTypes(RESOLVELanguage.INSTANCE,
                    Arrays.asList(Resolve.ruleNames));

    public static TokenElementType getTokenElementType(int type) {
        for (TokenElementType t : TOKEN_ELEMENT_TYPES) {
            if (t.getType() == type) {
                return t;
            }
        }
        throw new IllegalArgumentException("NOT FOUND");
    }

    public static RuleElementType getRuleElementType(int index) {
        for (RuleElementType t : RULE_ELEMENT_TYPES) {
            if (t.getRuleIndex() == index) {
                return t;
            }
        }
        throw new IllegalArgumentException("NOT FOUND");
    }

    public static final TokenSet COMMENTS =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.DOC_COMMENT,
                    ResolveLexer.LINE_COMMENT);

    public static final TokenSet WHITESPACES =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.WS);

    //Todo: Put the parameter mode keywords in their own set so and eventually
    //add an appropriate option to set them (as distinct from keywords) in the
    //colors page. Maybe a set for built-in ('o', '+', '||', 'union') operators would be good as well..
    public static final TokenSet KEYWORDS =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.ALTERS,
                    ResolveLexer.BY,
                    ResolveLexer.CLEARS,
                    ResolveLexer.CART_PROD,
                    ResolveLexer.CONCEPT,
                    ResolveLexer.END,
                    ResolveLexer.ENSURES,
                    ResolveLexer.EVALUATES,
                    ResolveLexer.EXEMPLAR,
                    ResolveLexer.EXTERNALLY,
                    ResolveLexer.FACILITY,
                    ResolveLexer.FAMILY,
                    ResolveLexer.FOR,
                    ResolveLexer.IMPLEMENTATION,
                    ResolveLexer.IMPLEMENTED,
                    ResolveLexer.IS,
                    ResolveLexer.OPERATION,
                    ResolveLexer.MODELED,
                    ResolveLexer.PRECIS,
                    ResolveLexer.PRESERVES,
                    ResolveLexer.PROCEDURE,
                    ResolveLexer.RECURSIVE,
                    ResolveLexer.REPLACES,
                    ResolveLexer.REQUIRES,
                    ResolveLexer.RESTORES,
                    ResolveLexer.TYPE,
                    ResolveLexer.UPDATES,
                    ResolveLexer.USES,
                    ResolveLexer.VAR);
}
