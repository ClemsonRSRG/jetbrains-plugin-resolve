package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResVarDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarProcessor extends ResScopeProcessorBase {

    @Nullable private final ResCompositeElement scope;

    public ResVarProcessor(@NotNull PsiElement origin, boolean completion) {
        this(origin, origin, completion, false);
    }

    public ResVarProcessor(@NotNull PsiElement requestedName,
                           @NotNull PsiElement origin, boolean completion,
                           boolean delegate) {
        super(requestedName, origin, completion);
        this.scope = getScope(origin);
    }

    @Nullable public static ResCompositeElement getScope(@Nullable PsiElement o) {
        /*GoForStatement forStatement = PsiTreeUtil.getParentOfType(o, GoForStatement.class);
        if (forStatement != null) return forStatement.getBlock();
        GoIfStatement ifStatement = PsiTreeUtil.getParentOfType(o, GoIfStatement.class);
        if (ifStatement != null) return ifStatement.getBlock();
        GoElseStatement elseStatement = PsiTreeUtil.getParentOfType(o, GoElseStatement.class);
        if (elseStatement != null) return elseStatement.getBlock();
        GoExprCaseClause exprCaseClause = PsiTreeUtil.getParentOfType(o, GoExprCaseClause.class);
        if (exprCaseClause != null) return exprCaseClause;
        GoCommClause commClause = PsiTreeUtil.getParentOfType(o, GoCommClause.class);
        if (commClause != null) return commClause;*/
        //return PsiTreeUtil.getParentOfType(o, GoBlock.class);
        return null;
    }

    @Override protected boolean condition(@NotNull PsiElement element) {
        return !(element instanceof ResVarDef);
    }

}
