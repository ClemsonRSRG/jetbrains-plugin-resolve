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
 * An abstract base class from which all RESOLVE math definition-like
 * constructs extend.
 *
 * <p>See note in {@link ResMathDefinitionDecl} as to why we don't extend
 * {@link edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl}.</p>
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
