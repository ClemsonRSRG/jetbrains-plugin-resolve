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
    public List<ResUsesSpecGroup> getUsesSpecGroups();

    //@NotNull
    // public List<ResModuleSpec> getSuperModuleSpecList();

    @NotNull
    public List<ResMathDefnDecl> getMathDefinitionDecls();

    @NotNull
    public List<ResMathDefnSig> getMathDefnSigs();

    @NotNull
    public List<ResTypeLikeNodeDecl> getTypes();

    @NotNull
    public List<ResFacilityDecl> getFacilities();

    @Nullable
    public ResModuleParameters getModuleParameters();

    @NotNull
    public List<ResTypeParamDecl> getGenericTypeParams();

    @NotNull
    public List<ResOperationLikeNode> getOperationLikeThings();

}
