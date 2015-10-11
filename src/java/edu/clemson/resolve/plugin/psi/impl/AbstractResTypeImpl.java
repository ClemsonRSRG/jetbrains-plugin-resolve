package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractResTypeImpl
        extends
            ResCompositeElementImpl implements ResType {

    public AbstractResTypeImpl(ASTNode node) {
        super(node);
    }

    @Override @Nullable
    public ResTypeReferenceExpression getTypeReferenceExpression() {
        return findChildByClass(ResTypeReferenceExpression.class);
    }
}
