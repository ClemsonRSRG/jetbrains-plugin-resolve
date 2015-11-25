package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResMathVarDeclGroup extends ResCompositeElement {

    @NotNull public List<ResMathVarDef> getMathVarDefList();

    @Nullable public ResCompositeElement getMathType();
}
