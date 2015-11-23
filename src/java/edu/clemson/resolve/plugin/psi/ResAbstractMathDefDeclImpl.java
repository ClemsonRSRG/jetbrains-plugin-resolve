package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractMathDefDeclImpl
        extends
            ResCompositeElementImpl implements ResMathDefDecl {

    public ResAbstractMathDefDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

}
