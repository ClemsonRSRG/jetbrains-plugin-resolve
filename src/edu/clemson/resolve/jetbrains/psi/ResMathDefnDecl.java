package edu.clemson.resolve.jetbrains.psi;

import java.util.List;

/** Represents any sort of mathematical definition in RESOLVE, including:
 *  categorical, inductive, and standard style defs.
 *  <p>
 *  <p>Notice too that this doesn't extend {@link ResNamedElement}; this has to
 *  do with the fact that the name portion of a definition isn't really part
 *  of the declaration itself, rather its bound up in an instance of
 *  {@link ResMathDefnSig}.</p>
 *
 *  @since 0.0.1
 */
public interface ResMathDefnDecl extends ResCompositeElement {

    /** Returns a list of {@link ResMathDefnSig}s introduced by this
     *  declaration. Note that this is going to be singleton except in the case
     *  of {@link ResMathCategoricalDefnDecl}s, in which there could be an
     *  arbitrary number of signatures introduced.
     *
     *  @return a list of defn signatures
     */
    public List<ResMathDefnSig> getSignatures();
}
