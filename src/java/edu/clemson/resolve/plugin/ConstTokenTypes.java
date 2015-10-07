package edu.clemson.resolve.plugin;

import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

/**
 * Created by daniel on 10/6/15.
 */
public class ConstTokenTypes {

    public static TokenElementType ID =
            RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID);
}
