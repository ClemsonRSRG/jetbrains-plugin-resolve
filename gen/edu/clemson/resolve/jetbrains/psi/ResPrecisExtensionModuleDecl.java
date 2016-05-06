// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResPrecisExtensionModuleDecl extends ResModuleDecl {

  @NotNull
  List<ResModuleIdentifierSpec> getModuleIdentifierSpecList();

  @Nullable
  ResPrecisBlock getPrecisBlock();

  @Nullable
  PsiElement getEnd();

  @NotNull
  PsiElement getExtension();

  @Nullable
  PsiElement getFor();

  @NotNull
  PsiElement getPrecis();

  @Nullable
  PsiElement getWith();

}
