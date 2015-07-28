package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.impl.ElementBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.RowIcon;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class AbstractDeclNode
        extends
            ASTWrapperPsiElement implements PsiNamedElement {

    protected String name = null;

    public AbstractDeclNode(@NotNull ASTNode node) {
        super(node);
    }

    public abstract boolean isPublic();

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

    @Nullable @Override public Icon getIcon(int flags) {
        Icon icon = null;
        if (this instanceof TypeModelDeclNode) icon = RESOLVEIcons.MODULE;
        //else if (this instanceof GoFunctionDeclaration) icon = GoIcons.FUNCTION;
        if (icon != null) {
            if ((flags & Iconable.ICON_FLAG_VISIBILITY) != 0) {
                final RowIcon rowIcon =
                        ElementBase.createLayeredIcon(this, icon, flags);
                rowIcon.setIcon(isPublic() ? PlatformIcons.PUBLIC_ICON :
                        PlatformIcons.PRIVATE_ICON, 1);
                return rowIcon;
            }
            return icon;
        }
        return super.getIcon(flags);
    }

    @Override public int getTextOffset() {
        PsiElement id = getId();
        if ( id!=null ) return id.getTextOffset();
        return super.getTextOffset();
    }
}
