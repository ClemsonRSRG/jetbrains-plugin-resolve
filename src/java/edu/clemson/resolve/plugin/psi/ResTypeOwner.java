package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface ResTypeOwner extends ResCompositeElement {

    @Nullable
    ResTypeReferenceExpression getTypeReferenceExpression();
}
