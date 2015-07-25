package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;

public class NamedTypeElementRefNode extends AbstractNamedElementRefNode {

    public NamedTypeElementRefNode(IElementType type, CharSequence text) {
        super(type, text);
    }

    @Override public IElementType getNamedRefType() {
        return RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.TYPE_REF);
    }
}
