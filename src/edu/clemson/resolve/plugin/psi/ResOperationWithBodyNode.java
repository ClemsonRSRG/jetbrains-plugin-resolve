package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResOperationWithBodyNode extends ResOperationLikeNode {

    @Nullable public ResBlock getOpBlock();
}
