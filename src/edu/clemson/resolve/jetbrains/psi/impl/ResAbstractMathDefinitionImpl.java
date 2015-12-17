package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.psi.ResMathDefinitionDecl;
import edu.clemson.resolve.jetbrains.psi.ResMathDefinitionSignature;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An abstract base class from which all RESOLVE math definition-like
 * constructs extend.
 *
 * <p>See note in {@link ResMathDefinitionDecl} as to why we don't extend
 * {@link edu.clemson.resolve.jetbrains.psi.impl.ResNamedElementImpl}.</p>
 */
public abstract class ResAbstractMathDefinitionImpl
        extends
            ResCompositeElementImpl implements ResMathDefinitionDecl {

    public ResAbstractMathDefinitionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public List<ResMathDefinitionSignature> getSignatures() {
        return PsiTreeUtil.getChildrenOfTypeAsList(
                this, ResMathDefinitionSignature.class);
    }
}
