package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDeclaration;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.stubs.ResOperationProcedureDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResOperationProcedureDeclarationImpl<T extends ResOperationProcedureDeclarationStub<?>>
        extends
            ResNamedElementImpl<T> implements ResOperationProcedureDeclaration {

    public ResOperationProcedureDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    @Override @Nullable public ResType getType() {
        return findChildByClass(ResType.class);
    }
}
