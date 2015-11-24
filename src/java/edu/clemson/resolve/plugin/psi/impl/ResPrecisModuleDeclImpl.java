package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.ResPrecisModuleDecl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class ResPrecisModuleDeclImpl
        extends
            ResModuleDeclImpl implements ResPrecisModuleDecl {

    public ResPrecisModuleDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResPrecisModuleDeclImpl(node);
        }
    }
}
