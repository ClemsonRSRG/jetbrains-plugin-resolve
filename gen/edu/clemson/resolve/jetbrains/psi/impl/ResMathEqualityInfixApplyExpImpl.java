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

public class ResMathEqualityInfixApplyExpImpl extends ResMathExpImpl implements ResMathEqualityInfixApplyExp {

  public ResMathEqualityInfixApplyExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathEqualityInfixApplyExp(this);
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
  public PsiElement getEquals() {
    return findChildByType(EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getNequals() {
    return findChildByType(NEQUALS);
  }

  @Override
  @Nullable
  public PsiElement getNequals1() {
    return findChildByType(NEQUALS1);
  }

}
