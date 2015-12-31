// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathPrefixDefinitionSignature extends ResMathDefinitionSignature {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  ResMathNameIdentifier getMathNameIdentifier();

  @NotNull
  List<ResMathVarDeclGroup> getMathVarDeclGroupList();

  @Nullable
  PsiElement getColon();

  @Nullable
  PsiElement getLparen();

  @Nullable
  PsiElement getRparen();

}
