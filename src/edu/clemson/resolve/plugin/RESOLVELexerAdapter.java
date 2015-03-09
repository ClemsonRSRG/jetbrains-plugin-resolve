package edu.clemson.resolve.plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class RESOLVELexerAdapter extends FlexAdapter {
    public RESOLVELexerAdapter() {
        super(new _RESOLVELexer((Reader) null));
    }
}