package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResJetbrainTypes;
import edu.clemson.resolve.plugin.psi.ResMathVarDeclGroup;
import edu.clemson.resolve.plugin.psi.ResMathVarDef;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable @Override public ResCompositeElement getMathType() {
        return findChildByType(ResJetbrainTypes.MATH_TYPE_EXP);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathVarDeclGroupImpl(node);
        }
    }
}
