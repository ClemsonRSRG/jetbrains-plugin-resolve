package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathParamDecl;
import edu.clemson.resolve.plugin.psi.ResMathSignature;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResAbstractMathSignatureImpl
        extends
            ResCompositeElementImpl implements ResMathSignature {

    public ResAbstractMathSignatureImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathParamDecl> getParameters() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathParamDecl.class);
    }
}
