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

/**
 * See note in {@link ResMathDefinitionDecl} as to why this doesn't extend
 * {@link edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl}
 */
public abstract class ResAbstractMathDefinitionDeclImpl
        extends
            ResCompositeElementImpl implements ResMathDefinitionDecl {

    public ResAbstractMathDefinitionDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public List<ResMathDefinitionSignature> getSignatures() {
        return PsiTreeUtil.getChildrenOfTypeAsList(
                this, ResMathDefinitionSignature.class);
    }
}
