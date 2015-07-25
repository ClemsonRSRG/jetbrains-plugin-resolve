package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;

public class TypeRefNode extends AbstractNamedElementRefNode {

    public TypeRefNode(IElementType type, CharSequence text) {
        super(type, text);
    }

    @Override public IElementType getNamedRefType() {
        return RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.TYPE_REF);
    }

    @Override public PsiReference getReference() {
        return new NamedPsiElementRef(this, getText());
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new TypeRefNode(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES
                    .get(ResolveLexer.TYPE_REF), node.getText());
        }
    }
}
