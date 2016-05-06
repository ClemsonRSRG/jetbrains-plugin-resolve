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

public class ResMathSetInfixApplyExpImpl extends ResMathExpImpl implements ResMathSetInfixApplyExp {

  public ResMathSetInfixApplyExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathSetInfixApplyExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResMathExp> getMathExpList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathExp.class);
  }

  @Override
  @Nullable
  public PsiElement getColoncolon() {
    return findChildByType(COLONCOLON);
  }

  @Override
  @Nullable
  public PsiElement getIsIn() {
    return findChildByType(IS_IN);
  }

  @Override
  @Nullable
  public PsiElement getIsIn1() {
    return findChildByType(IS_IN1);
  }

  @Override
  @Nullable
  public PsiElement getIsNotIn() {
    return findChildByType(IS_NOT_IN);
  }

  @Override
  @Nullable
  public PsiElement getIsNotIn1() {
    return findChildByType(IS_NOT_IN1);
  }

}
