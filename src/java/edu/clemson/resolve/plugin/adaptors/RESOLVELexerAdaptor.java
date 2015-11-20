package edu.clemson.resolve.plugin.adaptors;

import com.intellij.lang.Language;
import org.antlr.intellij.adaptor.lexer.AntlrLexerAdapter;
import org.antlr.intellij.adaptor.lexer.SimpleAntlrAdapter;
import org.antlr.v4.runtime.Lexer;

/** Adapts RESOLVE/Antlr specific needs to IntelliJ's. */
public class RESOLVELexerAdaptor extends SimpleAntlrAdapter {
    public RESOLVELexerAdaptor(Language language, Lexer lexer) {
        super(language, lexer);
    }
}
