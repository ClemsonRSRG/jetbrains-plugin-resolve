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
import com.intellij.psi.ResolveState;

public class ResTypeReprDeclImpl extends ResAbstractTypeLikeNodeImpl implements ResTypeReprDecl {

  public ResTypeReprDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitTypeReprDecl(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResConventionsClause getConventionsClause() {
    return findChildByClass(ResConventionsClause.class);
  }

  @Override
  @Nullable
  public ResCorrespondenceClause getCorrespondenceClause() {
    return findChildByClass(ResCorrespondenceClause.class);
  }

  @Override
  @Nullable
  public ResType getType() {
    return findChildByClass(ResType.class);
  }

  @Override
  @Nullable
  public ResTypeImplInit getTypeImplInit() {
    return findChildByClass(ResTypeImplInit.class);
  }

  @Override
  @Nullable
  public PsiElement getEquals() {
    return findChildByType(EQUALS);
  }

  @Override
  @NotNull
  public PsiElement getFamilyType() {
    return findNotNullChildByType(FAMILY_TYPE);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @Nullable
  public ResType getResTypeInner(ResolveState context) {
    return ResPsiImplUtil.getResTypeInner(this, context);
  }

}
