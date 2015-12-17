package edu.clemson.resolve.jetbrains.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.jetbrains.RESOLVEParserDefinition;

public class ResolveLexer extends MergingLexerAdapter {

    public ResolveLexer() {
        super(new FlexAdapter(new _ResLexer()),
                TokenSet.orSet(RESOLVEParserDefinition.COMMENTS,
                        RESOLVEParserDefinition.WHITESPACES));
    }
}
