package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResModuleDecl extends ResNamedElement {

    @NotNull public ResModuleBlock getModuleBlock();

    @NotNull public List<ResUsesSpec> getUsesSpecs();

    /*@NotNull public List<ResFacilityDecl> getFacilities();

    @NotNull public List<ResOperationDecl> getOperations();
*/
    @NotNull public List<ResOperationWithBodyNode> getOperationsWithImpls();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();
}
