package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.Nullable;

public interface ResExpression extends ResTypeOwner {

    @Nullable ResTypeRefNode getResType(ResolveState context);
}
