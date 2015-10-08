package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.stubs.ResTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractResTypeImpl
        extends
            ResStubbedElementImpl<ResTypeStub> implements ResType {

    public AbstractResTypeImpl(ASTNode node) {
        super(node);
    }

    public AbstractResTypeImpl(ResTypeStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    @Override @Nullable
    public ResTypeReferenceExpression getTypeReferenceExpression() {
        return findChildByClass(ResTypeReferenceExpression.class);
    }
}
