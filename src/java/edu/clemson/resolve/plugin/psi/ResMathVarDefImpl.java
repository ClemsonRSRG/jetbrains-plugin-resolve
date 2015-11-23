package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class ResMathVarDefImpl
        extends
            ResNamedElementImpl implements ResMathVarDef {

    public ResMathVarDefImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ResJetbrainTypes.ID);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathVarDefImpl(node);
        }
    }
}
