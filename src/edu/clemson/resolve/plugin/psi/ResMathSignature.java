package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResMathSignature extends ResCompositeElement {

    @NotNull public List<ResMathParamDecl> getParameters();

    @Nullable public ResMathResult getMathResult();
}
