package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathDefinitionDecl;
import edu.clemson.resolve.plugin.psi.ResMathInfixDefinitionSignature;
import edu.clemson.resolve.plugin.psi.ResMathDefinitionSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResAbstractMathDefinitionDeclImpl
        extends
            ResNamedElementImpl implements ResMathDefinitionDecl {

    public ResAbstractMathDefinitionDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public List<ResMathDefinitionSignature> getSignatures() {
        return PsiTreeUtil.getChildrenOfTypeAsList(
                this, ResMathDefinitionSignature.class);
    }
}
