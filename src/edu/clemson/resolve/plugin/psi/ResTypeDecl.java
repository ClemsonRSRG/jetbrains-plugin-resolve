package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Encompasses type model declarations and type representation declarations.
 */
public interface ResTypeDecl extends ResTopLevelModuleDecl, ResNamedElement {
    @NotNull PsiElement getIdentifier();
}
