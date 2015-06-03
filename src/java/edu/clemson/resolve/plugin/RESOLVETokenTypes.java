package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;

import java.util.Arrays;

public class RESOLVETokenTypes {

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
