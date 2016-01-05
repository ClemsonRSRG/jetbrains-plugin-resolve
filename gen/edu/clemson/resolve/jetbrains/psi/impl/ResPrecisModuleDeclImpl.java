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

public class ResPrecisModuleDeclImpl extends ResAbstractModuleImpl implements ResPrecisModuleDecl {

  public ResPrecisModuleDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitPrecisModuleDecl(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResPrecisBlock getPrecisBlock() {
    return findChildByClass(ResPrecisBlock.class);
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
  @NotNull
  public PsiElement getPrecis() {
    return findNotNullChildByType(PRECIS);
  }

}
