package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//Doesn't extend NamedElement since the name isn't part of the definition part:
//it exists in the signature. All a definitionDecl part really does is pair a
//body expr with a signature. Even in categorical defs, the actual meat of the
//definitions are contained in the signature. The Decl part is really just a
//pairing mechanism then.
public interface ResMathDefDecl extends ResCompositeElement {

    @NotNull public List<ResMathDefSig> getMathDefSignatures();
}
