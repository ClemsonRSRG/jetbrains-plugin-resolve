package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface TypeOwner extends ResolveCompositeElement {

    @Nullable
    TypeReferenceExpression getTypeReferenceExpression();
}
