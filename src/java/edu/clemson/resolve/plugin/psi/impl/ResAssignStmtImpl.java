package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResAssignStmt;
import edu.clemson.resolve.plugin.psi.ResExpression;
import edu.clemson.resolve.plugin.psi.ResLeftHandExpr;
import edu.clemson.resolve.plugin.psi.ResStmt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResAssignStmtImpl
        extends
            ResCompositeElementImpl implements ResAssignStmt {

    public ResAssignStmtImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public ResExpression getAssignmentExp() {
        return PsiTreeUtil.getChildOfType(this, ResExpression.class);
    }

    @Override @NotNull public ResLeftHandExpr getLeftHandExpr() {
        return findNotNullChildByClass(ResLeftHandExpr.class);
    }
}
