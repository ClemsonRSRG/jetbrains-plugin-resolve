package edu.clemson.resolve.plugin.adaptors;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.parser.ResolveParser;
import org.antlr.intellij.adaptor.parser.AntlrParser;
import org.antlr.intellij.adaptor.parser.SyntaxErrorListener;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class RESOLVELanguageParser extends AntlrParser<ResolveParser> {

    public RESOLVELanguageParser() {
        super(RESOLVELanguage.INSTANCE);
    }

    @Override
    protected ResolveParser createParserImpl(TokenStream tokenStream,
                                             IElementType root,
                                             PsiBuilder builder) {
        ResolveParser parser = new ResolveParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        return parser;
    }

    @Override
    protected ParseTree parseImpl(ResolveParser parser, IElementType root, PsiBuilder builder) {
        return null;
    }
}
