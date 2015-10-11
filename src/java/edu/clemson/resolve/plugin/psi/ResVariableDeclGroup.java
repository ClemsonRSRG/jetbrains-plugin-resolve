package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResVariableDeclGroup extends ResCompositeElement {

    @NotNull List<ResVarDefinition> getParamDefinitionList();

    @NotNull ResType getType();
}