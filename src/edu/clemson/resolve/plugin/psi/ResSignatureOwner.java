package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface ResSignatureOwner extends ResCompositeElement {

    @Nullable ResSignature getSignature();
}
