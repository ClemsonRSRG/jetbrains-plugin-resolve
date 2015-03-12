package edu.clemson.resolve.plugin.adaptors;

import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.AntlrLexerState;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.IntegerStack;
import org.antlr.v4.runtime.misc.MurmurHash;

/**
 * Created by daniel on 3/11/15.
 */
public class RESOLVELexerState extends AntlrLexerState {
    /** Tracks whether we are in a lexer rule, a parser rule or neither;
     *  managed by the ANTLRv4Lexer grammar.
     */
    private final int currentRuleType;

    public ANTLRv4LexerState(int mode, IntegerStack modeStack, int currentRuleType) {
        super(mode, modeStack);
        this.currentRuleType = currentRuleType;
    }

    public int getCurrentRuleType() {
        return currentRuleType;
    }

    @Override
    public void apply(Lexer lexer) {
        super.apply(lexer);
        if (lexer instanceof ResolveLexer) {
            ((ResolveLexer)lexer).setCurrentRuleType(getCurrentRuleType());
        }
    }

    @Override
    protected int hashCodeImpl() {
        int hash = MurmurHash.initialize();
        hash = MurmurHash.update(hash, getMode());
        hash = MurmurHash.update(hash, getModeStack());
        hash = MurmurHash.update(hash, getCurrentRuleType());
        return MurmurHash.finish(hash, 3);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RESOLVELexerState)) {
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }

        RESOLVELexerState other = (RESOLVELexerState)obj;
        return this.getCurrentRuleType() == other.getCurrentRuleType();
    }
}
