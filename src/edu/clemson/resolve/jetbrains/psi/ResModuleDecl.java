package edu.clemson.resolve.jetbrains.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents the root of all module like declarations in RESOLVE.
 */
public interface ResModuleDecl extends ResNamedElement {

    @NotNull
    public ResBlock getBlock();

    @NotNull
    public List<ResUsesItem> getUsesItems();

    @NotNull
    public List<ResModuleSpec> getSuperModuleSpecList();

    @NotNull
    public List<ResMathDefinitionDecl> getMathDefinitionDecls();

    @NotNull
    public List<ResMathDefinitionSignature> getMathDefinitionSignatures();

    @NotNull
    public List<ResTypeLikeNodeDecl> getTypes();

    @NotNull
    public List<ResFacilityDecl> getFacilities();

    @Nullable
    public ResModuleParameters getModuleParameters();

    @NotNull
    public List<ResOperationLikeNode> getOperationLikeThings();

}
