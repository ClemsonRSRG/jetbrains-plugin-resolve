package edu.clemson.resolve.jetbrains.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** The signature of a mathematical definition ({@link ResMathDefinitionDecl}).
 *  This holds the 'meat' of a definition including its name, 'return type'
 *  (which in this case is just a {@link ResMathExp}), and any formal parameters.
 */
public interface ResMathDefinitionSignature extends ResNamedElement {

    @NotNull public List<ResMathVarDeclGroup> getParameters();

    /** Returns the 'math type' of this {@code ResMathDefinitionSignature}.
     *  Best to think of the {@code MathTypeExp} simply a synonym for a regular
     *  {@link ResMathExp} that happens to be functioning as a type.
     *
     *  @return the declared 'math type' of this signature
     */
    @Nullable public ResMathExp getMathTypeExp();
}
