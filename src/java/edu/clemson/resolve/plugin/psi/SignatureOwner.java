package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface SignatureOwner extends ResolveCompositeElement {

    @Nullable Signature getSignature();
}
