package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeLikeNodeImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Encompasses type model declarations and type representation declarations.
 * Implementations of common methods between the two constructs can be found
 * in {@link ResAbstractTypeLikeNodeImpl}.
 *
 * @since 0.0.1
 */
public interface ResTypeLikeNodeDecl extends ResNamedElement {

    @NotNull public PsiElement getIdentifier();
}
