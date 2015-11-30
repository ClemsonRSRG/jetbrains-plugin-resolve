package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResModuleDecl extends ResNamedElement {

    @NotNull public ResBlock getBlock();

    @NotNull public List<ResMathDefinitionDecl> getMathDefinitionDecls();

    @NotNull public List<ResMathDefinitionSignature> getMathDefinitionSignatures();

    //@NotNull public List<ResUsesSpec> getUsesSpecs();

    /*@NotNull public List<ResFacilityDecl> getFacilities();

    @NotNull public List<ResOperationDecl> getOperations();

    @NotNull public List<ResOperationWithBodyNode> getOperationsWithImpls();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();
    */
}
