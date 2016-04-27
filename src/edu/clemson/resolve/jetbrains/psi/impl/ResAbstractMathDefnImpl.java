package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.psi.ResMathDefnDecl;
import edu.clemson.resolve.jetbrains.psi.ResMathDefnSig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/** An abstract base class from which all RESOLVE math definition-like
 *  constructs extend.
 *  <p>
 *  <p>See note in {@link ResMathDefnDecl} as to why we don't extend
 *  {@link edu.clemson.resolve.jetbrains.psi.impl.ResNamedElementImpl}.</p>
 */
public abstract class ResAbstractMathDefnImpl
        extends
        ResCompositeElementImpl implements ResMathDefnDecl {

    public ResAbstractMathDefnImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public List<ResMathDefnSig> getSignatures() {
        return PsiTreeUtil.getChildrenOfTypeAsList(
                this, ResMathDefnSig.class);
    }
}
