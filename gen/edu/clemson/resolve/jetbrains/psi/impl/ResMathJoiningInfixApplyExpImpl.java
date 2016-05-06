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

public class ResMathJoiningInfixApplyExpImpl extends ResMathExpImpl implements ResMathJoiningInfixApplyExp {

  public ResMathJoiningInfixApplyExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathJoiningInfixApplyExp(this);
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
  public PsiElement getCat() {
    return findChildByType(CAT);
  }

  @Override
  @Nullable
  public PsiElement getColoncolon() {
    return findChildByType(COLONCOLON);
  }

  @Override
  @Nullable
  public PsiElement getIntersect() {
    return findChildByType(INTERSECT);
  }

  @Override
  @Nullable
  public PsiElement getIntersect1() {
    return findChildByType(INTERSECT1);
  }

  @Override
  @Nullable
  public PsiElement getIntersectPlus() {
    return findChildByType(INTERSECT_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getUnion() {
    return findChildByType(UNION);
  }

  @Override
  @Nullable
  public PsiElement getUnion1() {
    return findChildByType(UNION1);
  }

  @Override
  @Nullable
  public PsiElement getUnionPlus() {
    return findChildByType(UNION_PLUS);
  }

}
