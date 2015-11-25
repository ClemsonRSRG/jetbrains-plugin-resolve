package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.*;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResMathDefSigImpl
        extends
            ResNamedElementImpl implements ResMathDefSig {

    public ResMathDefSigImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public ResCompositeElement getMathType() {
        return findChildByType(ResJetbrainTypes.MATH_TYPE_EXP);
    }

    @NotNull @Override public List<ResMathVarDeclGroup> getMathVarDeclGroups() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathVarDeclGroup.class);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findChildByType(ResJetbrainTypes.ID);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathDefSigImpl(node);
        }
    }
}
