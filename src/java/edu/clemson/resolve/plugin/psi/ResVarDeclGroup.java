package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResVarDeclGroup extends ResCompositeElement {

    @NotNull List<ResVarDefinition> getFieldDefinitionList();

    @NotNull ResType getType();
}
