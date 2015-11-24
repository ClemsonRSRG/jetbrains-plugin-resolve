package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.psi.ResJetbrainTypes;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResMathSymbolRefExpImpl
        extends
            ResCompositeElementImpl implements ResMathSymbolRefExp {

    public ResMathSymbolRefExpImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResJetbrainTypes.ID);
    }

    @NotNull public PsiReference getReference() {
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable @Override public ResMathSymbolRefExp getQualifier() {
        return null;
    }
}
