package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResJetbrainTypes;
import edu.clemson.resolve.plugin.psi.ResModuleDecl;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.boilerplate.impl.ResPrecisModuleDeclImpl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResModuleDeclImpl
        extends
            ResNamedElementImpl implements ResModuleDecl {

    public ResModuleDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findChildByType(ResJetbrainTypes.ID);
    }

    @Nullable @Override public ResType findSiblingType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResType.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResPrecisModuleDeclImpl(node);
        }
    }
}
