package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDeclNode
        extends
            ASTWrapperPsiElement implements PsiNamedElement {

    protected String name = null;

    public AbstractDeclNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override public String getName() {
        if ( name!=null ) return name;
        PsiElement id = getId();
        if ( id!=null ) return id.getText();
        return "unknown-name";
    }

    public abstract PsiElement getId();

    @Override public PsiElement setName(@NonNls @NotNull String name)
            throws IncorrectOperationException {
		/*
		From doc: "Creating a fully correct AST node from scratch is
		          quite difficult. Thus, surprisingly, the easiest way to
		          get the replacement node is to create a dummy file in the
		          custom language so that it would contain the necessary
		          node in its parse tree, build the parse tree and
		          extract the necessary node from it.
		 */
//		System.out.println("rename "+this+" to "+name);
        PsiElement id = getId();
        id.replace(MyPsiUtils.createLeafFromText(getProject(),
                getContext(), name,
                RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID)));
        this.name = name;
        return this;
    }

    @Override public int getTextOffset() {
        PsiElement id = getId();
        if ( id!=null ) return id.getTextOffset();
        return super.getTextOffset();
    }
}
