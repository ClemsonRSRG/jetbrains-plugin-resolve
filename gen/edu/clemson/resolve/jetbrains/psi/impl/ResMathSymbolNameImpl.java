// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static edu.clemson.resolve.jetbrains.ResTypes.*;
import edu.clemson.resolve.jetbrains.psi.*;

public class ResMathSymbolNameImpl extends ResCompositeElementImpl implements ResMathSymbolName {

  public ResMathSymbolNameImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathSymbolName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getCat() {
    return findChildByType(CAT);
  }

  @Override
  @Nullable
  public PsiElement getFalse() {
    return findChildByType(FALSE);
  }

  @Override
  @Nullable
  public PsiElement getGreater() {
    return findChildByType(GREATER);
  }

  @Override
  @Nullable
  public PsiElement getGreaterOrEqual() {
    return findChildByType(GREATER_OR_EQUAL);
  }

  @Override
  @Nullable
  public PsiElement getGreaterOrEqual1() {
    return findChildByType(GREATER_OR_EQUAL1);
  }

  @Override
  @Nullable
  public PsiElement getLess() {
    return findChildByType(LESS);
  }

  @Override
  @Nullable
  public PsiElement getLessOrEqual() {
    return findChildByType(LESS_OR_EQUAL);
  }

  @Override
  @Nullable
  public PsiElement getLessOrEqual1() {
    return findChildByType(LESS_OR_EQUAL1);
  }

  @Override
  @Nullable
  public PsiElement getLessOrEqualU() {
    return findChildByType(LESS_OR_EQUAL_U);
  }

  @Override
  @Nullable
  public PsiElement getMinus() {
    return findChildByType(MINUS);
  }

  @Override
  @Nullable
  public PsiElement getMul() {
    return findChildByType(MUL);
  }

  @Override
  @Nullable
  public PsiElement getNot() {
    return findChildByType(NOT);
  }

  @Override
  @Nullable
  public PsiElement getNot1() {
    return findChildByType(NOT1);
  }

  @Override
  @Nullable
  public PsiElement getPlus() {
    return findChildByType(PLUS);
  }

  @Override
  @Nullable
  public PsiElement getPreccurlyeq() {
    return findChildByType(PRECCURLYEQ);
  }

  @Override
  @Nullable
  public PsiElement getQuotient() {
    return findChildByType(QUOTIENT);
  }

  @Override
  @Nullable
  public PsiElement getTrue() {
    return findChildByType(TRUE);
  }

  @Override
  @Nullable
  public PsiElement getUnionPlus() {
    return findChildByType(UNION_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getVrod() {
    return findChildByType(VROD);
  }

  @Override
  @Nullable
  public PsiElement getIdentifier() {
    return findChildByType(IDENTIFIER);
  }

  @Override
  @Nullable
  public PsiElement getInt() {
    return findChildByType(INT);
  }

}
