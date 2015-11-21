package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResModuleDecl
        extends
            ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    @NotNull public ResModuleBlock getModuleBlock();

    @Nullable public PsiElement getIdentifier();

    @NotNull public List<ResUsesSpec> getUsesSpecs();

    @NotNull public List<ResFacilityDecl> getFacilities();

    @NotNull public List<ResOperationDecl> getOperations();

    @NotNull public List<ResOperationWithBodyNode> getOperationsWithImpls();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();
}
