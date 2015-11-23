package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.jetbrains.annotations.NotNull;

public abstract class ResAbstractMathDefDecl
        extends
            ResNamedElementImpl implements ResMathDefDecl {

    public ResAbstractMathDefDecl(@NotNull ASTNode node) {
        super(node);
    }


}
