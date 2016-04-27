package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResAbstractMathSigImpl
        extends
        ResNamedElementImpl implements ResMathDefnSig {

    public ResAbstractMathSigImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public List<ResMathVarDeclGroup> getParameters() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathVarDeclGroup.class);
    }

    @Nullable
    @Override
    public ResMathExp getMathTypeExp() {
        return findChildByClass(ResMathExp.class);
    }

    /**
     * This has to be {@code Nullable} at the moment; think about it: We have
     * infix and outfix signatures, how would we create the |..| needed?
     * In other words, it's tough doing completion for non-identifier like things
     */
    @Nullable
    @Override
    public PsiElement getIdentifier() {
        return findChildByClass(ResMathSymbolName.class);
    }
}
