package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public abstract class RAbstractModuleImpl extends ASTWrapperPsiElement {

    public RAbstractModuleImpl(@NotNull ASTNode node) {
        super(node);
    }
}
