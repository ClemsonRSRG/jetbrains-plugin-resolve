package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface ResType extends ResCompositeElement {

    @Nullable
    ResTypeReferenceExpression getTypeReferenceExpression();
}
