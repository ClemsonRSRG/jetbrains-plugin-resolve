package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ResModuleDecl extends ResProgNamedElement {

    @NotNull public ResModuleBlock getModuleBlock();

    @NotNull public List<ResUsesSpec> getUsesSpecs();

    /*@NotNull public List<ResFacilityDecl> getFacilities();

    @NotNull public List<ResOperationDecl> getOperations();

    @NotNull public List<ResOperationWithBodyNode> getOperationsWithImpls();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();
    */
}
