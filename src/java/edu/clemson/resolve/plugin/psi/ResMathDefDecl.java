package edu.clemson.resolve.plugin.psi;

//Doesn't extend NamedElement since the name isn't part of the definition part,
//it exists in the signature. When you  think about it, all a definitionDecl part
//does is pair a body expr with a signature. Even in categorical defs, the actual
//definitions are all given as signatures. The Decl part is just a pairing mechanism then.
public interface ResMathDefDecl extends ResCompositeElement {


}
