package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResMathPrefixDefSigImpl extends ResMathDefSigImpl {

    public ResMathPrefixDefSigImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        List<ResMathVarDeclGroup> x = this.getMathVarDeclGroups();

        return findChildByType(ResJetbrainTypes.ID);
    }

    @NotNull @Override public List<ResMathVarDeclGroup> getMathVarDeclGroups() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathVarDeclGroup.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathPrefixDefSigImpl(node);
        }
    }
}
