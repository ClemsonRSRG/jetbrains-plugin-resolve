package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResModule;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractModule
        extends
            ResCompositeElementImpl implements ResModule {

    public ResAbstractModule(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES
                .get(ResolveLexer.ID));
    }

    @Nullable @Override public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Override public PsiReference getReference() {
        return null;
    }

    @Override public boolean isPublic() {
        return true;
    }

    @NotNull @Override public PsiElement setName(
            @NonNls @NotNull String newName)
            throws IncorrectOperationException {
        PsiElement identifier = getIdentifier();
        identifier.replace(ResElementFactory
                .createIdentifierFromText(getProject(), newName));
        return this;
    }

}
