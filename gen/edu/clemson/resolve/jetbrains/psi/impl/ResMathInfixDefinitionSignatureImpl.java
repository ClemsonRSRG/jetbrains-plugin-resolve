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

public class ResMathInfixDefinitionSignatureImpl extends ResAbstractMathSignatureImpl implements ResMathInfixDefinitionSignature {

  public ResMathInfixDefinitionSignatureImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) ((ResVisitor)visitor).visitMathInfixDefinitionSignature(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResMathExp getMathExp() {
    return findChildByClass(ResMathExp.class);
  }

  @Override
  @NotNull
  public ResMathNameIdentifier getMathNameIdentifier() {
    return findNotNullChildByClass(ResMathNameIdentifier.class);
  }

  @Override
  @NotNull
  public List<ResMathSingletonVarDecl> getMathSingletonVarDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathSingletonVarDecl.class);
  }

  @Override
  @Nullable
  public PsiElement getColon() {
    return findChildByType(COLON);
  }

}
