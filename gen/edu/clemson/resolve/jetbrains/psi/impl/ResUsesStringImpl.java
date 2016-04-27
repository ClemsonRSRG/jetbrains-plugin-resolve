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
import com.intellij.psi.PsiDirectory;

public class ResUsesStringImpl extends ResCompositeElementImpl implements ResUsesString {

  public ResUsesStringImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitUsesString(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getString() {
    return findNotNullChildByType(STRING);
  }

  @Nullable
  public PsiDirectory resolve() {
    return ResPsiImplUtil.resolve(this);
  }

  @NotNull
  public String getPath() {
    return ResPsiImplUtil.getPath(this);
  }

  @NotNull
  public TextRange getPathTextRange() {
    return ResPsiImplUtil.getPathTextRange(this);
  }

}
