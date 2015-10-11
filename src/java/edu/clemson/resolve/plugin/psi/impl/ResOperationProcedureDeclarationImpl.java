package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDeclaration;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.stubs.ResOperationProcedureDeclarationStub;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResOperationProcedureDeclarationImpl
        extends ResNamedElementImpl<ResOperationProcedureDeclarationStub<?>>
        implements ResOperationProcedureDeclaration {

    public ResOperationProcedureDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    @Override @Nullable public ResType getType() {
        return findChildByClass(ResType.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResOperationProcedureDeclarationImpl(node);
        }
    }
}
