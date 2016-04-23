package edu.clemson.resolve.jetbrains.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.jetbrains.RESOLVEParserDefinition;
import edu.clemson.resolve.jetbrains.ResTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(ResTypes.LPAREN, ResTypes.RPAREN, false),
            new BracePair(ResTypes.LBRACK, ResTypes.RBRACK, false),
            new BracePair(ResTypes.LBRACE, ResTypes.RBRACE, false),
            new BracePair(ResTypes.DBL_LBRACE, ResTypes.DBL_RBRACE, false),
    };

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(
            @NotNull IElementType lbraceType, @Nullable IElementType type) {
        return RESOLVEParserDefinition.COMMENTS.contains(type)
                || RESOLVEParserDefinition.WHITESPACES.contains(type)
                || type == ResTypes.SEMICOLON
                || type == ResTypes.COMMA
                || type == ResTypes.RPAREN
                || type == ResTypes.RBRACK
                || type == ResTypes.RBRACE
                || null == type;
    }

    @Override
    public int getCodeConstructStart(PsiFile file,
                                     int openingBraceOffset) {
        return openingBraceOffset;
    }
}
