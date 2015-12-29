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

public class ResTypeModelDeclImpl extends ResAbstractTypeLikeNodeImpl implements ResTypeModelDecl {

  public ResTypeModelDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitTypeModelDecl(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResConstraintsClause getConstraintsClause() {
    return findChildByClass(ResConstraintsClause.class);
  }

  @Override
  @Nullable
  public ResIntializationClause getIntializationClause() {
    return findChildByClass(ResIntializationClause.class);
  }

  @Override
  @Nullable
  public ResMathExp getMathExp() {
    return findChildByClass(ResMathExp.class);
  }

  @Override
  @Nullable
  public PsiElement getBy() {
    return findChildByType(BY);
  }

  @Override
  @Nullable
  public PsiElement getExemplar() {
    return findChildByType(EXEMPLAR);
  }

  @Override
  @NotNull
  public PsiElement getFamily() {
    return findNotNullChildByType(FAMILY);
  }

  @Override
  @NotNull
  public PsiElement getFamilyType() {
    return findNotNullChildByType(FAMILY_TYPE);
  }

  @Override
  @Nullable
  public PsiElement getIs() {
    return findChildByType(IS);
  }

  @Override
  @Nullable
  public PsiElement getModeled() {
    return findChildByType(MODELED);
  }

}
