// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResModuleIdentifierSpec extends ResNamedElement {

  @NotNull
  ResModuleIdentifier getModuleIdentifier();

  @Nullable
  ResModuleLibraryIdentifier getModuleLibraryIdentifier();

  @Nullable
  PsiElement getAs();

  @Nullable
  PsiElement getFrom();

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  ResModuleLibraryIdentifier getFromLibraryIdentifier();

  @Nullable
  PsiElement getAlias();

  @NotNull
  String getName();

}
