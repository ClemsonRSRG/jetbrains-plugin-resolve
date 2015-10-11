package edu.clemson.resolve.plugin.psi.impl.scopeprocessing;

import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDeclaration;
import edu.clemson.resolve.plugin.psi.ResVarDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarProcessor extends ResScopeProcessorBase {

    @Nullable private final ResCompositeElement myScope;

    public ResVarProcessor(@NotNull PsiElement origin, boolean completion) {
        this(origin, origin, completion, false);
    }

    protected boolean condition(@NotNull PsiElement psiElement) {
        return !(psiElement instanceof ResVarDefinition);//&&
                //!(psiElement instanceof ResFieldDefinition);
    }

    public ResVarProcessor(@NotNull PsiElement requestedName,
                           @NotNull PsiElement origin, boolean completion,
                           boolean delegate) {
        super(requestedName, origin, completion);
        myScope = getScope(origin);
    }

    private boolean differentBlocks(@Nullable ResNamedElement o) {
        return !Comparing.equal(myScope, getScope(o));
    }

    @Nullable public static ResCompositeElement getScope(
            @Nullable PsiElement o) {
        ResOperationProcedureDeclaration opProcedureDecl =
                PsiTreeUtil.getParentOfType(o, ResOperationProcedureDeclaration.class);
        if (opProcedureDecl != null) return opProcedureDecl;

        return null;
        /*ResForStatement forStatement = PsiTreeUtil.getParentOfType(o, ResForStatement.class);
        if (forStatement != null) return forStatement.getBlock();
        ResForStatement ifStatement = PsiTreeUtil.getParentOfType(o, ResForStatement.class);
        if (ifStatement != null) return ifStatement.getBlock();
        ResForStatement elseStatement = PsiTreeUtil.getParentOfType(o, ResForStatement.class);
        if (elseStatement != null) return elseStatement.getBlock();
        ResForStatement exprCaseClause = PsiTreeUtil.getParentOfType(o, ResForStatement.class);
        if (exprCaseClause != null) return exprCaseClause;*/
        //return PsiTreeUtil.getParentOfType(o, GoBlock.class);
    }

}
