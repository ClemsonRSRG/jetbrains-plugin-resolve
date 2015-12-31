// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathInfixDefinitionSignature extends ResMathDefinitionSignature {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  ResMathNameIdentifier getMathNameIdentifier();

  @NotNull
  List<ResMathSingletonVarDecl> getMathSingletonVarDeclList();

  @Nullable
  PsiElement getColon();

}
