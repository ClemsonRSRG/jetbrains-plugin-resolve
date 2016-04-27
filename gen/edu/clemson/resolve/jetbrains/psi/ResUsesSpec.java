// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResUsesSpec extends ResNamedElement {

  @NotNull
  ResUsesString getUsesString();

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  String getAlias();

  boolean shouldGoDeeper();

  @NotNull
  String getPath();

  @Nullable
  String getName();

}
