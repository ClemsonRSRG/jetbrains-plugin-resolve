package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;

public interface ResLeftHandExpr extends ResCompositeElement {

    @NotNull ResExpression getExpression();
}
