package edu.clemson.resolve.plugin.psi;

import java.util.List;

/**
 * Represents any sort of mathematical definition in RESOLVE, including:
 * categorical, inductive, and standard style defs.
 *
 * <p>Notice too that this doesn't extend {@link ResNamedElement}; this has to
 * do with the fact that the name portion of a defininition isn't really part
 * of the definition itself, rather it exists in an instance of
 * {@link ResMathDefinitionSignature}.</p>
 *
 * @since 0.0.1
 */
public interface ResMathDefinitionDecl extends ResCompositeElement {

    /**
     * Returns a list of {@link ResMathDefinitionSignature}s introduced by this
     * declaration. Note that this is going to be singleton except in the case
     * of {@link ResCategoricalDefinitionDecl}s, in which there could be an
     * arbitrary number of signatures introduced.
     *
     * @return a list of def signatures
     */
    public List<ResMathDefinitionSignature> getSignatures();
}
