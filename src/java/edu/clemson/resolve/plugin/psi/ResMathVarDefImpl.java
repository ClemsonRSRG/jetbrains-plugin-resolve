package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
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
}
