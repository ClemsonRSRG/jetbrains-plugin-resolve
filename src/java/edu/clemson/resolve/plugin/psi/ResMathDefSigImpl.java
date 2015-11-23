package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//We exist as an intermediate node (we're always going to be the middle of a
//chain of rules, never a leaf; why are we not abstract then? well, every node
//needs to be concrete right now since there's no mechanism of hiding irrelevant
//children.

//TODO: We might actually not need this... Mess around with ResCompositeElement shouldGoDeeper()...
public class ResMathDefSigImpl
        extends
            ResNamedElementImpl implements ResMathDefSig {

    public ResMathDefSigImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        ResMathDefSig signature = PsiTreeUtil.findChildOfType(this,
                ResMathDefSig.class);
        if (signature != null) {
            return signature.getIdentifier();
        }
        return null;
    }

    @NotNull @Override public List<ResMathVarDeclGroup> getMathVarDeclGroups() {
        List<ResMathVarDeclGroup> result = new ArrayList<ResMathVarDeclGroup>();
        ResMathDefSig signature = PsiTreeUtil.findChildOfType(this,
                ResMathDefSig.class);
        if (signature != null) {
            result.addAll(signature.getMathVarDeclGroups());
        }
        return result;
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResMathDefSigImpl(node);
        }
    }
}
