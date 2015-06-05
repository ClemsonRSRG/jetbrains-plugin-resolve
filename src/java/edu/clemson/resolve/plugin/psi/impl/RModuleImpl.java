package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewUtil;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class RModuleImpl extends ASTWrapperPsiElement {

    public RModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public ItemPresentation getPresentation() {
        final String text = UsageViewUtil.createNodeText(this);
        if (text != null) {
            return new ItemPresentation() {
                @Nullable
                @Override
                public String getPresentableText() {
                    return getName();
                }

                @Nullable
                @Override
                public String getLocationString() {
                    return getContainingFile().getName();
                }

                @Nullable
                @Override
                public Icon getIcon(boolean b) {
                    return RModuleImpl.this.getIcon(0);
                }
            };
        }
        return super.getPresentation();
    }
}
