package edu.clemson.resolve.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import grammars.edu.plugin._RESOLVELexer;
import grammars.edu.plugin.parser.RESOLVEParser;
import java.edu.clemson.resolve.plugin.psi.RESOLVEPrecisFile;
import grammars.edu.plugin.psi.RESOLVETypes;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public class RESOLVEParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES =
            TokenSet.create(TokenType.WHITE_SPACE);
    //public static final TokenSet COMMENTS =
    //    TokenSet.create(RESOLVETypes.COMMENT);
    public static final IFileElementType FILE =
            new IFileElementType(Language.
                    <RESOLVELanguage>findInstance(RESOLVELanguage.class));
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        //return new FlexAdapter(new _RESOLVELexer((Reader) null));
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return null;//COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return null;//new RESOLVEParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return null;//new RESOLVEPrecisFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    /**
     * Convert from internal parse node (AST they call it) to final PSI node.
     * This converts only internal rule nodes apparently, not leaf nodes. Leafs
     * are just tokens I guess.
     */
    @NotNull
    public PsiElement createElement(ASTNode node) {
        return null; //RESOLVETypes.Factory.createElement(node);
    }
}
