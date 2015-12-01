package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResMathDefinitionSignature extends ResNamedElement {

    @NotNull public List<ResMathVarDeclGroup> getParameters();

    /**
     * Returns the 'math type' of this {@code ResMathDefinitionSignature}.
     * Best to think of the {@code MathTypeExp} simply a synonym for an
     * {@link ResMathExp} that happens to be functioning as a type.
     *
     * @return the declared 'math type' of this signature
     */
    @Nullable public ResMathExp getMathTypeExp();
}
