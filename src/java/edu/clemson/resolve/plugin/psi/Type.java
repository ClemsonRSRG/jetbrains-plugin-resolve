package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface Type extends ResolveCompositeElement {

    @Nullable
    TypeReferenceExpression getTypeReferenceExpression();
}
