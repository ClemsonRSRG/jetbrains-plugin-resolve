package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Signature extends ResolveCompositeElement {

    @NotNull List<ParameterDeclaration> getParameterDeclarationList();

    @Nullable Type getType();
}
