package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public interface ResModule extends ResNamedElement {

    @NotNull public ResModuleBlock getModuleBlock();

    @NotNull public List<ResUsesSpec> getUsesSpecs();

    @NotNull public List<ResFacilityDecl> getFacilities();

    @NotNull public List<ResTypeLikeNodeDecl> getTypes();
}
