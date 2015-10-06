package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResSignature extends ResCompositeElement {

    @NotNull List<ResParameterDeclarationGroup> getParameterDeclarationList();

    @Nullable
    ResType getType();
}
