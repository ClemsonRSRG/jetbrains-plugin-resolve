package edu.clemson.resolve.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.psi.ResolveFileNode;
import edu.clemson.resolve.plugin.adaptors.RESOLVELanguageParser;
import edu.clemson.resolve.plugin.adaptors.RESOLVELexerAdaptor;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.jetbrains.annotations.NotNull;

public class RESOLVEParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE =
            new IFileElementType(RESOLVELanguage.INSTANCE);

    @NotNull @Override public Lexer createLexer(Project project) {
        ResolveLexer lexer = new ResolveLexer(null);
        return new RESOLVELexerAdaptor(RESOLVELanguage.INSTANCE, lexer);
    }

    @NotNull public TokenSet getWhitespaceTokens() {
        return RESOLVETokenTypes.WHITESPACES;
    }

    @NotNull public TokenSet getCommentTokens() {
        return RESOLVETokenTypes.COMMENTS;
    }

    @NotNull public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull public PsiParser createParser(final Project project) {
        return new RESOLVELanguageParser();
    }

    @Override public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new ResolveFileNode(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    /**
     * Convert from internal parse node (AST they call it) to final PSI node.
     * This converts only internal rule nodes apparently, not leaf nodes. Leafs
     * are just tokens I guess.
     */
    @NotNull public PsiElement createElement(ASTNode node) {
        return RESOLVEASTFactory.createInternalParseTreeNode(node);
    }
}
