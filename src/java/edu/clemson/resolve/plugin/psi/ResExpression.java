package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.ResolveState;
import edu.clemson.resolve.plugin.psi.ResTypeOwner;
import org.jetbrains.annotations.Nullable;

public interface ResExpression extends ResTypeOwner {

    @Nullable ResType getResType(ResolveState context);
}
