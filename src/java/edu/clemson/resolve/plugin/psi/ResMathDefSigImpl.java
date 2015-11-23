package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//We exist as an intermediate node (we're always going to be the middle of a
//chain of rules, never a leaf; why are we not abstract then? well, every node
//needs to be concrete right now since there's no mechanism of hiding irrelevant
//children.
public class ResMathDefSigImpl
        extends
            ResNamedElementImpl implements ResMathDefSig {

    public ResMathDefSigImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        ResMathDefSig child =
                PsiTreeUtil.findChildOfType(this, ResMathDefSig.class);
        if (child != null) {
            return child.getIdentifier();
        }
        return null;
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathDefSigImpl(node);
        }
    }
}
