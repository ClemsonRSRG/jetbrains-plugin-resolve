package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

public interface ResOperationWithBodyNode extends ResOperationLikeNode {

    @NotNull public ResBlock getBlock();
}
