package edu.clemson.resolve.plugin.adaptors;

import com.intellij.lang.Language;
import org.antlr.intellij.adaptor.lexer.AntlrLexerAdapter;
import org.antlr.intellij.adaptor.lexer.AntlrLexerState;
import org.antlr.intellij.adaptor.lexer.SimpleAntlrAdapter;
import org.antlr.v4.runtime.Lexer;

/** Adapts RESOLVE/Antlr specific needs to IntelliJ's. */
public class RESOLVELexerAdaptor extends SimpleAntlrAdapter {

    private static final AntlrLexerState INITIAL_STATE =
            new AntlrLexerState(Lexer.DEFAULT_MODE, null);

    public RESOLVELexerAdaptor(Language language, Lexer lexer) {
        super(language, lexer);
    }

    @Override protected AntlrLexerState getInitialState() {
        return INITIAL_STATE;
    }

    @Override protected AntlrLexerState getLexerState(Lexer lexer) {
        if (lexer._modeStack.isEmpty()) {
            return new AntlrLexerState(lexer._mode, null);
        }
        return new AntlrLexerState(lexer._mode, lexer._modeStack);
    }
}
