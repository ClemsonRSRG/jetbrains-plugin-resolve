// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResFacilityDecl extends ResNamedElement {

  @NotNull
  List<ResExtensionPairing> getExtensionPairingList();

  @NotNull
  List<ResModuleArgList> getModuleArgListList();

  @NotNull
  List<ResModuleIdentifier> getModuleIdentifierList();

  @NotNull
  List<ResModuleLibraryIdentifier> getModuleLibraryIdentifierList();

  @Nullable
  PsiElement getBy();

  @Nullable
  PsiElement getExternally();

  @NotNull
  PsiElement getFacility();

  @Nullable
  PsiElement getImplemented();

  @Nullable
  PsiElement getIs();

  @Nullable
  PsiElement getSemicolon();

  @NotNull
  PsiElement getIdentifier();

  @Nullable
  ResFile resolveSpecification();

  @Nullable
  ResFile resolveImplementation();

}
