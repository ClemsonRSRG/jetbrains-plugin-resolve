package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResMathVarDeclImpl
        extends
            ResCompositeElementImpl implements ResMathVarDecl {

    public ResMathVarDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    //This will be a singleton list here (for this node); as opposed to the
    //group node.
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
