package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResRecordType extends ResType {

    @NotNull List<ResVarDeclGroup> getVariableDeclGroups();
}
