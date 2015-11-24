package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.ResJetbrainTypes;
import edu.clemson.resolve.plugin.psi.ResRefExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResRefExpImpl extends ResCompositeElementImpl implements ResRefExp {

    public ResRefExpImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResJetbrainTypes.ID);
    }

    @Nullable @Override public ResRefExp getQualifier() {
        return null;
    }
}
