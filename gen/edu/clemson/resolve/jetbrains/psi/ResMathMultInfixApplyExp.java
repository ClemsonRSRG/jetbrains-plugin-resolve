// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathMultInfixApplyExp extends ResMathInfixApplyExp {

  @NotNull
  List<ResMathExp> getMathExpList();

  @Nullable
  PsiElement getColoncolon();

  @Nullable
  PsiElement getHtimes();

  @Nullable
  PsiElement getMod();

  @Nullable
  PsiElement getMul();

  @Nullable
  PsiElement getQuotient();

  @Nullable
  PsiElement getTimes();

}
