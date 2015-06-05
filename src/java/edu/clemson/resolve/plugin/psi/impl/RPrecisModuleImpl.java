package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RPrecisModuleImpl extends RModuleImpl {

    public RPrecisModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new RPrecisModuleImpl(node);
        }
    }

    @Override public Icon getIcon(int flags) {
        return RESOLVEIcons.PRECIS;
    }
}
