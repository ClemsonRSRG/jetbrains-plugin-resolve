package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResAbstractMathDefinitionDeclImpl extends ResNamedElementImpl {

    public ResAbstractMathDefinitionDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable ResMathSignature getMathSignature() {
        return PsiTreeUtil.findChildOfType(this, ResMathSignature.class);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return null;
    }
}
