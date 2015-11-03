package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeLikeDeclImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Encompasses type model declarations and type representation declarations.
 * Implementations of common methods between the two constructs can be found
 * in {@link ResAbstractTypeLikeDeclImpl}.
 */
public interface ResTypeLikeNodeDecl extends ResNamedElement {
    @NotNull PsiElement getIdentifier();

}
