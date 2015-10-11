package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ResFacilityModule extends ResAbstractModule {

    public ResFacilityModule(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public Icon getIcon(int i) {
        return RESOLVEIcons.FACILITY;
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResFacilityModule(node);
        }
    }
}
