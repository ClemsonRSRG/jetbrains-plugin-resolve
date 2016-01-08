// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathBooleanInfixApplyExp extends ResMathExp {

  @NotNull
  List<ResMathExp> getMathExpList();

  @Nullable
  PsiElement getAnd();

  @Nullable
  PsiElement getColoncolon();

  @Nullable
  PsiElement getIff();

  @Nullable
  PsiElement getImplies();

  @Nullable
  PsiElement getIsIn();

  @Nullable
  PsiElement getIsNotIn();

  @Nullable
  PsiElement getOr();

}
