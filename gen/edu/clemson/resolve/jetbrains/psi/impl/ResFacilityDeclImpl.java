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

public class ResFacilityDeclImpl extends ResNamedElementImpl implements ResFacilityDecl {

  public ResFacilityDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitFacilityDecl(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResArgumentList> getArgumentListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResArgumentList.class);
  }

  @Override
  @NotNull
  public List<ResModuleSpec> getModuleSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleSpec.class);
  }

  @Override
  @Nullable
  public PsiElement getBy() {
    return findChildByType(BY);
  }

  @Override
  @Nullable
  public PsiElement getExternally() {
    return findChildByType(EXTERNALLY);
  }

  @Override
  @NotNull
  public PsiElement getFacility() {
    return findNotNullChildByType(FACILITY);
  }

  @Override
  @Nullable
  public PsiElement getImplemented() {
    return findChildByType(IMPLEMENTED);
  }

  @Override
  @Nullable
  public PsiElement getIs() {
    return findChildByType(IS);
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
  public ResFile getSpecification() {
    return ResPsiImplUtil.getSpecification(this);
  }

}
