package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
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

    @NotNull @Override public PsiReference getReference() {
        return ResPsiImplUtil.getReference(this);
    }

    @Nullable public PsiFile resolve() {
        return ResPsiImplUtil.resolve(this);
    }

    @NotNull public TextRange getUsesTextRange() {
        String text = getText();
        return text != null && !text.isEmpty() ?
                TextRange.create(0, text.length() - 1) : TextRange.EMPTY_RANGE;
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResUsesItemImpl(node);
        }
    }
}
