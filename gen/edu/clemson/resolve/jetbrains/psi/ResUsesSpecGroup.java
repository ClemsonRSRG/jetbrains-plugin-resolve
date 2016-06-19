// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResUsesSpecGroup extends ResCompositeElement {

  @NotNull
  List<ResModuleIdentifierSpec> getModuleIdentifierSpecList();

  @Nullable
  ResModuleLibraryIdentifierSpec getModuleLibraryIdentifierSpec();

  @Nullable
  PsiElement getFrom();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  ResModuleLibraryIdentifierSpec getFromModuleLibraryIdentifier();

}
