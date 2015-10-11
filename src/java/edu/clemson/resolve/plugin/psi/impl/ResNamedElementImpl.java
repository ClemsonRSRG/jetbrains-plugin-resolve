package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.ElementBase;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.RowIcon;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ResNamedElementImpl
        extends
            ResCompositeElementImpl implements ResCompositeElement, ResNamedElement {

    public ResNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public boolean isPublic() {
        return true;
    }

    @Nullable @Override public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Nullable @Override public String getName() {
        PsiElement identifier = getIdentifier();
        return identifier != null ? identifier.getText() : null;
    }

    @Override public int getTextOffset() {
        PsiElement identifier = getIdentifier();
        return identifier != null ? identifier.getTextOffset() :
                super.getTextOffset();
    }

    @Nullable @Override public ResType getResType(
            @Nullable final ResolveState context) {
        return getResTypeInner(context);
    }

    @Nullable protected ResType getResTypeInner(@Nullable ResolveState context) {
        return findSiblingType();
    }

    @Nullable @Override public Icon getIcon(int flags) {
        Icon icon = null;
        if (this instanceof ResTypeReprDecl) icon = RESOLVEIcons.REPR_TYPE;
        else if (this instanceof ResVarDefinition) icon = RESOLVEIcons.VARIABLE;
        //else if (this instanceof ResFieldDefinition) icon = RESOLVEIcons.FIELD;
        //else if (this instanceof ResOperationProcedureDeclaration) icon = RESOLVEIcons.FUNCTION;
        if (icon != null) {
            if ((flags & Iconable.ICON_FLAG_VISIBILITY) != 0) {
                final RowIcon rowIcon = ElementBase.createLayeredIcon(this, icon, flags);
                rowIcon.setIcon(isPublic() ? PlatformIcons.PUBLIC_ICON : PlatformIcons.PRIVATE_ICON, 1);
                return rowIcon;
            }
            return icon;
        }
        return super.getIcon(flags);
    }

    @Nullable @Override public ResType findSiblingType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResType.class);
    }

    @Override public PsiElement setName(@NotNull String s)
            throws IncorrectOperationException {
        PsiElement identifier = getIdentifier();
        if (identifier != null) {
            identifier.replace(ResElementFactory
                    .createIdentifierFromText(getProject(), s));
        }
        return this;
    }
}
