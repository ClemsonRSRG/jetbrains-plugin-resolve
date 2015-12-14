package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Any operation node that has a body that consists of zero or more
 * {@link ResStatement}s.
 *
 * @since 0.0.1
 */
public interface ResOperationWithBodyNode extends ResOperationLikeNode {

    @Nullable public ResBlock getOpBlock();
}
