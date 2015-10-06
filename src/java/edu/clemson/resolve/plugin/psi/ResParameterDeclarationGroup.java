package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResParameterDeclarationGroup extends ResCompositeElement {

    @NotNull List<ResParameterDefinition> getParamDefinitionList();

    @NotNull ResType getType();
}
