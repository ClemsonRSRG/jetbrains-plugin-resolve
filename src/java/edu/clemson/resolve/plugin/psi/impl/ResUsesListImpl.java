package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResUsesListImpl extends ResCompositeElementImpl {

    public ResUsesListImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public List<ResUsesItem> getUsesItems() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResUsesItem.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResUsesListImpl(node);
        }
    }
}
