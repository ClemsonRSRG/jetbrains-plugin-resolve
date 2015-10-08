package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.stubs.ResTypeReprDeclStub;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractTypeDecl
        extends
            ResNamedElementImpl<ResTypeReprDeclStub> {

    public ResAbstractTypeDecl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public abstract ResType getType();

    @NotNull public abstract PsiElement getIdentifier();
}
