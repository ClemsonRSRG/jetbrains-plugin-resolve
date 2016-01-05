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
import com.intellij.psi.scope.PsiScopeProcessor;

public class ResPrecisBlockImpl extends ResCompositeElementImpl implements ResPrecisBlock {

  public ResPrecisBlockImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitPrecisBlock(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResMathCategoricalDefinitionDecl> getMathCategoricalDefinitionDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathCategoricalDefinitionDecl.class);
  }

  @Override
  @NotNull
  public List<ResMathInductiveDefinitionDecl> getMathInductiveDefinitionDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathInductiveDefinitionDecl.class);
  }

  @Override
  @NotNull
  public List<ResMathStandardDefinitionDecl> getMathStandardDefinitionDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathStandardDefinitionDecl.class);
  }

  @Override
  @NotNull
  public List<ResMathTheoremDecl> getMathTheoremDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathTheoremDecl.class);
  }

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return ResPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
