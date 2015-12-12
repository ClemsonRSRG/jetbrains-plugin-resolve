package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResModuleDecl extends ResNamedElement {

    @NotNull public ResBlock getBlock();

    @NotNull public List<ResUsesItem> getUsesItems();

    @NotNull public List<ResModuleSpec> getSuperModuleSpecList();

    @NotNull public List<ResMathDefinitionDecl> getMathDefinitionDecls();

    @NotNull public List<ResMathDefinitionSignature> getMathDefinitionSignatures();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();

    @NotNull public List<ResFacilityDecl> getFacilities();

    @Nullable public ResModuleParameters getModuleParameters();

    @NotNull public List<ResOperationLikeNode> getOperationLikeThings();

    /**
     * All module specs (names) referenced in the signature of a module; for
     * example, in the case of {@code Impl X for Y;}; this will contain the
     * {@code ResModuleSpec} for {@code Y}.
     */
    @NotNull public List<ResModuleSpec> getModuleSignatureSpecs();

}
