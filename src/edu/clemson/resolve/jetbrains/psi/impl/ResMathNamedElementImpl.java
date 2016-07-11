package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.ResMathSymbolName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResMathNamedElementImpl extends ResNamedElementImpl {

    public ResMathNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    /**
     * Named elements in mathematical contexts can have arbirary symbols as names, not just idents. This
     * method hooks in and returns that symbol as an 'identifier' -- in accordance with normal named elements
     * (e.g. in programmatic contexts).
     * @return
     */
    @Nullable
    @Override
    public PsiElement getIdentifier() {
        return findChildByClass(ResMathSymbolName.class);
    }
}
