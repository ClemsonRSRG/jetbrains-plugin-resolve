package edu.clemson.resolve.plugin;

import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.RuleElementType;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

/**
 * Created by daniel on 10/6/15.
 */
public class ConstEleTypes {

    public static TokenElementType ID =
            RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID);
    public static TokenElementType COLONCOLON =
            RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.COLONCOLON);
    public static RuleElementType STMT =
            RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_stmt);

}
