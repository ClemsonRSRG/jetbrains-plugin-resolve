// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathCategoricalDefinitionDecl extends ResMathDefinitionDecl {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  List<ResMathPrefixDefinitionSignature> getMathPrefixDefinitionSignatureList();

  @NotNull
  PsiElement getCategorical();

  @NotNull
  PsiElement getDefinition();

  @Nullable
  PsiElement getFor();

  @Nullable
  PsiElement getIs();

  @Nullable
  PsiElement getSemicolon();

}
