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

public class ResImplModuleDeclImpl extends ResAbstractModuleImpl implements ResImplModuleDecl {

  public ResImplModuleDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitImplModuleDecl(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResImplBlock getImplBlock() {
    return findChildByClass(ResImplBlock.class);
  }

  @Override
  @Nullable
  public ResImplModuleParameters getImplModuleParameters() {
    return findChildByClass(ResImplModuleParameters.class);
  }

  @Override
  @NotNull
  public List<ResModuleSpec> getModuleSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleSpec.class);
  }

  @Override
  @Nullable
  public ResRequiresClause getRequiresClause() {
    return findChildByClass(ResRequiresClause.class);
  }

  @Override
  @Nullable
  public ResUsesItemList getUsesItemList() {
    return findChildByClass(ResUsesItemList.class);
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(END);
  }

  @Override
  @Nullable
  public PsiElement getFor() {
    return findChildByType(FOR);
  }

  @Override
  @NotNull
  public PsiElement getImplementation() {
    return findNotNullChildByType(IMPLEMENTATION);
  }

  @Override
  @Nullable
  public PsiElement getOf() {
    return findChildByType(OF);
  }

}
