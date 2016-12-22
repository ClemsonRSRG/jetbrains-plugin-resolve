// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResTypeModelDecl extends ResTypeLikeNodeDecl {

  @Nullable
  ResConstraintsClause getConstraintsClause();

  @Nullable
  ResExemplarDecl getExemplarDecl();

  @Nullable
  ResIntializationClause getIntializationClause();

  @Nullable
  ResMathExp getMathExp();

  @Nullable
  PsiElement getBy();

  @NotNull
  PsiElement getFamily();

  @NotNull
  PsiElement getFamilyType();

  @Nullable
  PsiElement getIs();

  @Nullable
  PsiElement getModeled();

  @Nullable
  PsiElement getSemicolon();

  @NotNull
  PsiElement getIdentifier();

}
