package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.Nullable;

/**
 * Applies to any {@link ResNamedElement} or {@link ResExp} that could
 * potentially own (ie reference) a program type.
 */
public interface ResTypeOwner extends ResCompositeElement {

    @Nullable
    public ResType getResType(@Nullable ResolveState context);
}
