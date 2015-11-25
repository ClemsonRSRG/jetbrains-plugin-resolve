package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//Doesn't extend NamedElement since the name isn't part of the definition part:
//it exists in the signature. All a definitionDecl part really does is pair a
//body expr with a signature. Even in categorical defs, the actual meat of the
//definition is contained in the signature. The Decl part really just a
//pairing wrapper
public interface ResMathDefDecl extends ResCompositeElement {

    //We're a list to accomodate categorical defns; which are designed to
    //introduce (and group together) multiple related def sigs
    @NotNull public List<ResMathDefSig> getMathDefSignatures();
}
