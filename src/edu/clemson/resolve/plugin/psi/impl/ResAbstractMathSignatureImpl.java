package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathDefinitionSignature;
import edu.clemson.resolve.plugin.psi.ResMathExp;
import edu.clemson.resolve.plugin.psi.ResMathTypeExp;
import edu.clemson.resolve.plugin.psi.ResMathVarDeclGroup;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResAbstractMathSignatureImpl
        extends
            ResNamedElementImpl implements ResMathDefinitionSignature {

    public ResAbstractMathSignatureImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathVarDeclGroup> getParameters() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathVarDeclGroup.class);
    }

    @NotNull @Override public ResMathTypeExp getMathTypeExp() {
        return findNotNullChildByClass(ResMathTypeExp.class);
    }
}
