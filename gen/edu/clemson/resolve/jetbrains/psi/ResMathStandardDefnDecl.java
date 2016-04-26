// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathStandardDefnDecl extends ResMathDefinitionDecl {

  @Nullable
  ResMathExp getMathExp();

  @Nullable
  ResMathInfixDefnSig getMathInfixDefnSig();

  @Nullable
  ResMathOutfixDefnSig getMathOutfixDefnSig();

  @Nullable
  ResMathPostfixDefnSig getMathPostfixDefnSig();

  @Nullable
  ResMathPrefixDefnSig getMathPrefixDefnSig();

  @NotNull
  PsiElement getDefinition();

  @Nullable
  PsiElement getImplicit();

  @Nullable
  PsiElement getIs();

  @Nullable
  PsiElement getSemicolon();

}
