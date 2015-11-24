package edu.clemson.resolve.plugin.psi;

import edu.clemson.resolve.plugin.psi.impl.ResBlockImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResModuleDecl extends ResNamedElement {

    @Nullable public ResBlockImpl getBlock();

    @NotNull public List<ResMathDefDecl> getMathDefDecls();

    @NotNull public List<ResMathDefSig> getMathDefSigs();

}
