package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.impl.ResAbstractTypeLikeNodeImpl;
import org.jetbrains.annotations.NotNull;

/** Encompasses type model and representation declarations.
 *  Implementations of common methods between the two constructs can be found
 *  in {@link ResAbstractTypeLikeNodeImpl}.
 */
public interface ResTypeLikeNodeDecl extends ResNamedElement {

    @NotNull public PsiElement getIdentifier();
}
