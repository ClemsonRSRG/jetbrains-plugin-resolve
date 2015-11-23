package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResMathVarDeclGroup extends ResCompositeElement {

    @NotNull List<ResMathVarDef> getMathVarDefList();

    //TODO: @Nullable public ResMathType getMathType();
}
