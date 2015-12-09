package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResModuleDecl extends ResNamedElement {

    @NotNull public ResBlock getBlock();

    @NotNull public List<ResUsesItem> getUsesItems();

    @NotNull public List<ResModuleSpec> getModuleSpecList();

    @NotNull public List<ResMathDefinitionDecl> getMathDefinitionDecls();

    @NotNull public List<ResMathDefinitionSignature> getMathDefinitionSignatures();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();

    @NotNull public List<ResFacilityDecl> getFacilities();

    @Nullable public ResModuleParameters getModuleParameters();

    @NotNull public List<ResOperationLikeNode> getOperationLikeThings();

}
