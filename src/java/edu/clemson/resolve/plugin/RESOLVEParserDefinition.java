package edu.clemson.resolve.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.lexer.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.adaptors.RESOLVELanguageParser;
import edu.clemson.resolve.plugin.adaptors.RESOLVELexerAdaptor;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResTokenType;
import org.jetbrains.annotations.NotNull;

public class RESOLVEParserDefinition implements ParserDefinition {

    public static final IElementType LINE_COMMENT =
            new ResTokenType("RESOLVE_LINE_COMMENT");
    public static final IElementType MULTILINE_COMMENT =
            new ResTokenType("RESOLVE_MULTILINE_COMMENT");

    public static final IElementType WS =
            new ResTokenType("RESOLVE_WHITESPACE");
    public static final IElementType NLS =
            new ResTokenType("RESOLVE_WS_NEW_LINES");
    public static final TokenSet WHITESPACES = TokenSet.create(WS, NLS);
    public static final TokenSet COMMENTS =
            TokenSet.create(LINE_COMMENT, MULTILINE_COMMENT);

    @NotNull @Override public Lexer createLexer(Project project) {
        return new ResolveLexer();
    }
}
