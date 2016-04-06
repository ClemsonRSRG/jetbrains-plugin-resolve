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

public class ResMathPrefixGeneralizedCeilApplyExpImpl extends ResMathExpImpl implements ResMathPrefixGeneralizedCeilApplyExp {

  public ResMathPrefixGeneralizedCeilApplyExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitMathPrefixGeneralizedCeilApplyExp(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResMathExp> getMathExpList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathExp.class);
  }

  @Override
  @NotNull
  public PsiElement getLceil() {
    return findNotNullChildByType(LCEIL);
  }

  @Override
  @Nullable
  public PsiElement getRceil() {
    return findChildByType(RCEIL);
  }

}
