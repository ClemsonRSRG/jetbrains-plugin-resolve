package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ParameterDeclarationGroup extends ResolveCompositeElement {

    @NotNull List<ParameterDefinition> getParamDefinitionList();

    @NotNull Type getType();
}
