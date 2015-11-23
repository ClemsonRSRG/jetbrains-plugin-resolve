package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResMathDefSig extends ResNamedElement {

    @NotNull public List<ResMathVarDeclGroup> getMathVarDeclGroups();
}
