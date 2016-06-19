// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathPostfixDefnSig extends ResMathDefnSig {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  List<ResMathVarDecl> getMathVarDeclList();

  @Nullable
  PsiElement getColon();

  @Nullable
  PsiElement getLbrack();

  @NotNull
  PsiElement getLparen();

  @Nullable
  PsiElement getRbrack();

  @Nullable
  PsiElement getRparen();

}
