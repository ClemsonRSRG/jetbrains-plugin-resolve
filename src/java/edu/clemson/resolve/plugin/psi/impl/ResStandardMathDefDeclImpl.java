package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathDefSig;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResStandardMathDefDeclImpl extends ResAbstractMathDefDeclImpl {

    public ResStandardMathDefDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathDefSig> getMathDefSignatures() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathDefSig.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResStandardMathDefDeclImpl(node);
        }
    }
}
