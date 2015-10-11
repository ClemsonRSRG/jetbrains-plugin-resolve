package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.ResolveState;
import edu.clemson.resolve.plugin.psi.ResExpression;
import edu.clemson.resolve.plugin.psi.ResType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 10/11/15.
 */
public abstract class AbstractResExpressionImpl
        extends
            ResCompositeElementImpl implements ResExpression {

    public AbstractResExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public ResType getResType(ResolveState context) {
        return null;
    }
}
