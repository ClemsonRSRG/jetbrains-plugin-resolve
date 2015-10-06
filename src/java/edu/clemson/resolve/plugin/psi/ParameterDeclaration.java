package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ParameterDeclaration extends ResolveCompositeElement {

    @NotNull List<ParamDefinition> getParamDefinitionList();

    @NotNull Type getType();
}
