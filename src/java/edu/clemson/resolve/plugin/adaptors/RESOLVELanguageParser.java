package edu.clemson.resolve.plugin.adaptors;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.parser.Resolve;
import org.antlr.intellij.adaptor.parser.AntlrParser;
import org.antlr.intellij.adaptor.parser.SyntaxErrorListener;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A specific kind of parser that knows how to parse our general purpose
 * RESOLVE grammar type.
 */
public class RESOLVELanguageParser extends AntlrParser<Resolve> {

    public RESOLVELanguageParser() {
        super(RESOLVELanguage.INSTANCE);
    }

    @Override protected Resolve createParserImpl(TokenStream tokenStream,
                                             IElementType root,
                                             PsiBuilder builder) {
        Resolve parser = new Resolve(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        return parser;
    }

    @Override protected ParseTree parseImpl(Resolve parser,
                              IElementType root, PsiBuilder builder) {
        return parser.module();
    }
}
