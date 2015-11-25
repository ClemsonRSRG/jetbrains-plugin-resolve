package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResMathDefSig extends ResNamedElement {

    @Nullable public ResCompositeElement getMathType();

    @NotNull public List<ResMathVarDeclGroup> getMathVarDeclGroups();
}
