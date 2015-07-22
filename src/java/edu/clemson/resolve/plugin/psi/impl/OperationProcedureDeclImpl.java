package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class OperationProcedureDeclImpl extends ASTWrapperPsiElement {

    public OperationProcedureDeclImpl(ASTNode node) {
        super(node);
    }

    @NotNull public PsiElement getIdentifier() {
        return findNotNullChildByType(RESOLVETokenTypes
                .TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID));
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new OperationProcedureDeclImpl(node);
        }
    }
}
