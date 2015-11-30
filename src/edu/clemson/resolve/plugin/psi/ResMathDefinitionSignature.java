package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResMathDefinitionSignature extends ResNamedElement {

    @NotNull public List<ResMathVarDeclGroup> getParameters();

    /**
     * Returns the 'math type' of this {@code ResMathDefinitionSignature}.
     * Best to think of the {@link ResMathTypeExp} as simply a wrapper around an
     * arbitarary {@link ResMathExp}.
     *
     * @return the declared math type of this signature
     */
    @NotNull public ResMathTypeExp getMathTypeExp();
}
