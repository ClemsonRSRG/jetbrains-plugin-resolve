// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;

public interface ResUsesString extends ResCompositeElement {

  @NotNull
  PsiElement getString();

  @Nullable
  PsiDirectory resolve();

  @NotNull
  String getPath();

  @NotNull
  TextRange getPathTextRange();

}