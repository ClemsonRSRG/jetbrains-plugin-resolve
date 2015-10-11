package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResAssignStmt;
import edu.clemson.resolve.plugin.psi.ResExpression;
import edu.clemson.resolve.plugin.psi.ResStmt;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
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

    @Override @NotNull public ResAbstractVarExpOptions getLeftHandExpr() {
        ASTNode node = getNode();
        return findNotNullChildByClass(ResAbstractVarExpOptions.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResAssignStmtImpl(node);
        }
    }
}
