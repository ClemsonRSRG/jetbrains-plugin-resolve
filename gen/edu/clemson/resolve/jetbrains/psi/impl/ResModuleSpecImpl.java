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
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;

public class ResModuleSpecImpl extends ResNamedElementImpl implements ResModuleSpec {

  public ResModuleSpecImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitModuleSpec(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return ResPsiImplUtil.getReferences(this);
  }

  @Nullable
  public PsiFile resolve() {
    return ResPsiImplUtil.resolve(this);
  }

  @NotNull
  public TextRange getModuleSpecTextRange() {
    return ResPsiImplUtil.getModuleSpecTextRange(this);
  }

}
