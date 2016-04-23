package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.impl.ResAbstractTypeLikeNodeImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Encompasses type model and representation declarations.
 * Implementations of common methods between the two constructs can be found
 * in {@link ResAbstractTypeLikeNodeImpl}.
 */
public interface ResTypeLikeNodeDecl extends ResNamedElement {

    @NotNull
    public PsiElement getIdentifier();

    /**
     * Returns the {@link ResMathExp} type 'behind' this type declaration.
     * There are two real cases I see here: The first is where {@code this}
     * represents an instanceof {@link ResTypeModelDecl}, in which case to
     * get the type expression, all we need to do is return the expression
     * stored there (after the 'modeled by' bit).
     * <p>
     * In the second case however when we're a type representation,
     * we'll need to do some resolving to find the model (if there is one!)
     * and then grab the type from what comes back. If there is no model,
     * we'll likely have to create a cart_prod_exp ourselves using
     * PsiElementFactory... from the stuff users typed in... dunno.</p>
     *
     * @return the math type expression for this type declaration
     */
    @Nullable
    public ResMathExp getMathMetaTypeExp();
}
