package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathVarDeclGroup;
import edu.clemson.resolve.plugin.psi.ResMathVarDef;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResMathVarDeclGroupImpl
        extends
            ResCompositeElementImpl implements ResMathVarDeclGroup {

    public ResMathVarDeclGroupImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathVarDef> getMathVarDefList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathVarDef.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathVarDeclGroupImpl(node);
        }
    }
}
