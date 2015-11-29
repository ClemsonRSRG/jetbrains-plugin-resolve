package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathDefinitionDecl;
import edu.clemson.resolve.plugin.psi.ResMathInfixDefinitionSignature;
import edu.clemson.resolve.plugin.psi.ResMathDefinitionSignature;
import edu.clemson.resolve.plugin.psi.ResMathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractMathDefinitionDeclImpl
        extends
            ResNamedElementImpl implements ResMathDefinitionDecl {

    public ResAbstractMathDefinitionDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public ResMathDefinitionSignature getMathSignature() {
        return findChildByClass(ResMathDefinitionSignature.class);
    }

    @Nullable protected ResMathType getResTypeInner(
            @Nullable ResolveState context) {
        return findSiblingType();
    }

    @Nullable @Override public ResMathType findSiblingType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResMathType.class);
    }
}
