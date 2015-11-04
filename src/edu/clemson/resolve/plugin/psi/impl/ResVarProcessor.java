package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResBlock;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResConceptBlock;
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

    @Nullable public static ResCompositeElement getScope(
            @Nullable PsiElement o) {
        /*ResWhileStatement whileStatement = PsiTreeUtil
                .getParentOfType(o, ResWhileStatement.class);
        if (whileStatement != null) return whileStatement.getBlock();
        ResIfStatement ifStatement = PsiTreeUtil
                .getParentOfType(o, ResIfStatement.class);
        if (ifStatement != null) return ifStatement.getBlock();
        ResElseStatement elseStatement = PsiTreeUtil
                .getParentOfType(o, ResElseStatement.class);
        if (elseStatement != null) return elseStatement.getBlock();
       */
        //TODO: NOT RIGHT YET.. SHOULD JUST BE A GENERAL FXN BLOCK...
        return PsiTreeUtil.getParentOfType(o, ResBlock.class);
    }

    @Override protected boolean condition(@NotNull PsiElement element) {
        return !(element instanceof ResVarDef);
    }
}
