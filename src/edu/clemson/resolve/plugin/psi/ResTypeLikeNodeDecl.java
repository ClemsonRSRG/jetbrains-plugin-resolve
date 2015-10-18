package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Encompasses type model declarations and type representation declarations.
 *  Implementations of common methods between the two constructs can be found
 *  in {@link edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDeclImpl}.
 */
public interface ResTypeLikeNodeDecl extends ResNamedElement {
    @NotNull PsiElement getIdentifier();
}
