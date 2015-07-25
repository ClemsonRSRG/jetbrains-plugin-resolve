package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

public class TypeModelDeclNode extends AbstractDeclNode {

    public TypeModelDeclNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override public PsiElement getId() {
        return findNotNullChildByType(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES
                .get(ResolveLexer.ID));
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new TypeModelDeclNode(node);
        }
    }
}
