package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.Nullable;

public interface ResTypeOwner extends ResCompositeElement {

   @Nullable ResTypeRefNode getResTypeRefNode(@Nullable ResolveState context);
}
