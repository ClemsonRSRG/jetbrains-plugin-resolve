package edu.clemson.resolve.jetbrains.editor;

import com.intellij.codeInsight.editorActions.JavaLikeQuoteHandler;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.jetbrains.ResTypes;
import org.jetbrains.annotations.NotNull;

public class RESOLVEQuoteHandler extends SimpleTokenSetQuoteHandler implements JavaLikeQuoteHandler {

  private static final TokenSet SINGLE_LINE_LITERALS = TokenSet.create(ResTypes.STRING);

  public RESOLVEQuoteHandler() {
    super(ResTypes.STRING, ResTypes.RAW_STRING, ResTypes.CHAR, TokenType.BAD_CHARACTER);
  }

  @Override
  public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
    return true;
  }

  @Override
  public TokenSet getConcatenatableStringTokenTypes() {
    return SINGLE_LINE_LITERALS;
  }

  @Override
  public String getStringConcatenationOperatorRepresentation() {
    return "+";
  }

  @Override
  public TokenSet getStringTokenTypes() {
    return SINGLE_LINE_LITERALS;
  }

  @Override
  public boolean isAppropriateElementTypeForLiteral(@NotNull IElementType tokenType) {
    return true;
  }

  @Override
  public boolean needParenthesesAroundConcatenation(PsiElement element) {
    return false;
  }
}
