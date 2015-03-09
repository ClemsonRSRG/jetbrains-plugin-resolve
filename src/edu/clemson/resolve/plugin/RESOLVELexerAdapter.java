package edu.clemson.resolve.plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class RESOLVELexerAdapter extends FlexAdapter {
    public RESOLVELexerAdapter() {
        super(new RESOLVELexer((Reader) null));
    }
}