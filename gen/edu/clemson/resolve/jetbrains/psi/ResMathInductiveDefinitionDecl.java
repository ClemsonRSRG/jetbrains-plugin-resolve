// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathInductiveDefinitionDecl extends ResMathDefinitionDecl {

  @NotNull
  List<ResMathExp> getMathExpList();

  @Nullable
  ResMathInfixDefinitionSignature getMathInfixDefinitionSignature();

  @Nullable
  ResMathOutfixDefinitionSignature getMathOutfixDefinitionSignature();

  @Nullable
  ResMathPrefixDefinitionSignature getMathPrefixDefinitionSignature();

  @NotNull
  PsiElement getDefinition();

  @NotNull
  PsiElement getInductive();

  @Nullable
  PsiElement getIndBase();

  @Nullable
  PsiElement getIndHypo();

  @Nullable
  PsiElement getIs();

}
