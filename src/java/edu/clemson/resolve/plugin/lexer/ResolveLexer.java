package edu.clemson.resolve.plugin.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.RESOLVEParserDefinition;

public class ResolveLexer extends MergingLexerAdapter {

    public ResolveLexer() {
        super(new FlexAdapter(new _ResolveLexer()), TokenSet.orSet(RESOLVEParserDefinition.COMMENTS, RESOLVEParserDefinition.WHITESPACES));
    }
}
