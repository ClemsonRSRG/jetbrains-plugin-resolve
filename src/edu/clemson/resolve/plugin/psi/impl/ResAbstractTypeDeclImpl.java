package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeDeclImpl extends ResNamedElementImpl {

    public ResAbstractTypeDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return null;
    }
}
