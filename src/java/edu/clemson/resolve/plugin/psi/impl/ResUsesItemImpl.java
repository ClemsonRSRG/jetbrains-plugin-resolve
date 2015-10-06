package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResUsesItemImpl
        extends
            ResCompositeElementImpl implements ResUsesItem {

    public ResUsesItemImpl(ASTNode node) {
        super(node);
    }

    @Override @Nullable public PsiElement getIdentifier() {
        return findChildByType(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES
                .get(ResolveLexer.ID));
    }

    @NotNull public PsiReference[] getReferences() {
        return ResPsiImplUtil.getReferences(this);
    }

    @Nullable public PsiDirectory resolve() {
        return ResPsiImplUtil.resolve(this);
    }
}
