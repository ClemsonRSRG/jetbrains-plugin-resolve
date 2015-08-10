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

    public static final TokenSet COMMENTS =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.DOC_COMMENT,
                    ResolveLexer.LINE_COMMENT);

    public static final TokenSet PARAMETER_MODES =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.UPDATES, ResolveLexer.RESTORES, ResolveLexer.REPLACES,
                    ResolveLexer.CLEARS, ResolveLexer.EVALUATES, ResolveLexer.ALTERS,
                    ResolveLexer.PRESERVES);

    public static final TokenSet BUILTIN_OPERATORS =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames), ResolveLexer.AND,
                    ResolveLexer.ASSIGN, ResolveLexer.BAR, ResolveLexer.CAT,
                    ResolveLexer.CUTMINUS, ResolveLexer.DBL_BAR, ResolveLexer.DIVIDE,
                    ResolveLexer.EQUALS, ResolveLexer.GT, ResolveLexer.GTE,
                    ResolveLexer.IMPLIES, ResolveLexer.INTERSECT, ResolveLexer.IS_IN,
                    ResolveLexer.IS_NOT_IN, ResolveLexer.LT, ResolveLexer.LTE,
                    ResolveLexer.MINUS, ResolveLexer.MULT, ResolveLexer.NEQUALS,
                    ResolveLexer.OR, ResolveLexer.PLUS, ResolveLexer.PLUSPLUS,
                    ResolveLexer.RANGE, ResolveLexer.RARROW, ResolveLexer.SWAP,
                    ResolveLexer.TILDE, ResolveLexer.TRIPLEDOT, ResolveLexer.UNION);

    public static final TokenSet WHITESPACES =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.WS);

    public static final TokenSet KEYWORDS =
            ElementTypeFactory.createTokenSet(RESOLVELanguage.INSTANCE,
                    Arrays.asList(ResolveLexer.tokenNames),
                    ResolveLexer.PRECIS,
                    ResolveLexer.BY,
                    ResolveLexer.CART_PROD,
                    ResolveLexer.CATEGORICAL,
                    ResolveLexer.CONCEPT,
                    ResolveLexer.CONSTRAINT,
                    ResolveLexer.CONVENTION,
                    ResolveLexer.COROLLARY,
                    ResolveLexer.CORRESPONDENCE,
                    ResolveLexer.DEFINITION,
                    ResolveLexer.DO,
                    ResolveLexer.ELSE,
                    ResolveLexer.END,
                    ResolveLexer.ENSURES,
                    ResolveLexer.ENTAILS,
                    ResolveLexer.ENHANCEMENT,
                    ResolveLexer.ENHANCED,
                    ResolveLexer.EXEMPLAR,
                    ResolveLexer.EXISTS,
                    ResolveLexer.EXTERNALLY,
                    ResolveLexer.FACILITY,
                    ResolveLexer.FAMILY,
                    ResolveLexer.FOR,
                    ResolveLexer.FORALL,
                    ResolveLexer.IF,
                    ResolveLexer.IMPLICIT,
                    ResolveLexer.INITIALIZATION,
                    ResolveLexer.IS,
                    ResolveLexer.IMPLEMENTATION,
                    ResolveLexer.IMPLEMENTED,
                    ResolveLexer.INDUCTIVE,
                    ResolveLexer.LAMBDA,
                    ResolveLexer.MODELED,
                    ResolveLexer.OF,
                    ResolveLexer.ON,
                    ResolveLexer.OTHERWISE,
                    ResolveLexer.OPERATION,
                    ResolveLexer.PROCEDURE,
                    ResolveLexer.RECORD,
                    ResolveLexer.RECURSIVE,
                    ResolveLexer.REQUIRES,
                    ResolveLexer.THEOREM,
                    ResolveLexer.TYPE,
                    ResolveLexer.USES,
                    ResolveLexer.VAR,
                    ResolveLexer.WHILE);
}
