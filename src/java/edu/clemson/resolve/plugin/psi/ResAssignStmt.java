package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResAssignStmt extends ResStmt {

    @Nullable ResExpression getAssignmentExp();

    @NotNull ResLeftHandExpr getLeftHandExpr();
}
