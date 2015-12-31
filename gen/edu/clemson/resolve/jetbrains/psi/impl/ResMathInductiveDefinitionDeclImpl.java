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

public class ResMathInductiveDefinitionDeclImpl extends ResAbstractMathDefinitionImpl implements ResMathInductiveDefinitionDecl {

  public ResMathInductiveDefinitionDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitMathInductiveDefinitionDecl(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResMathExp> getMathExpList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathExp.class);
  }

  @Override
  @Nullable
  public ResMathInfixDefinitionSignature getMathInfixDefinitionSignature() {
    return findChildByClass(ResMathInfixDefinitionSignature.class);
  }

  @Override
  @Nullable
  public ResMathOutfixDefinitionSignature getMathOutfixDefinitionSignature() {
    return findChildByClass(ResMathOutfixDefinitionSignature.class);
  }

  @Override
  @Nullable
  public ResMathPrefixDefinitionSignature getMathPrefixDefinitionSignature() {
    return findChildByClass(ResMathPrefixDefinitionSignature.class);
  }

  @Override
  @NotNull
  public PsiElement getDefinition() {
    return findNotNullChildByType(DEFINITION);
  }

  @Override
  @NotNull
  public PsiElement getInductive() {
    return findNotNullChildByType(INDUCTIVE);
  }

  @Override
  @Nullable
  public PsiElement getIndBase() {
    return findChildByType(IND_BASE);
  }

  @Override
  @Nullable
  public PsiElement getIndHypo() {
    return findChildByType(IND_HYPO);
  }

  @Override
  @Nullable
  public PsiElement getIs() {
    return findChildByType(IS);
  }

}
