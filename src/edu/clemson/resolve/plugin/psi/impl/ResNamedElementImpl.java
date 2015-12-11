package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.ElementBase;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.RowIcon;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ResNamedElementImpl
        extends
            ResCompositeElementImpl implements ResCompositeElement, ResNamedElement {

    public ResNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    public boolean isPublic() {
        return true;
    }

    @Nullable @Override public String getName() {
        PsiElement identifier = getIdentifier();
        return identifier != null ? identifier.getText() : null;
    }

    @NotNull @Override public PsiElement setName(
            @NonNls @NotNull String newName) throws IncorrectOperationException {
        PsiElement identifier = getIdentifier();
        if (identifier != null) {
            identifier.replace(ResElementFactory
                    .createIdentifierFromText(getProject(), newName));
        }
        return this;
    }

    @Override public int getTextOffset() {
        PsiElement identifier = getIdentifier();
        return identifier != null ? identifier.getTextOffset()
                : super.getTextOffset();
    }

    @Override public boolean processDeclarations(
            @NotNull PsiScopeProcessor processor,
            @NotNull ResolveState state,
            PsiElement lastParent,
            @NotNull PsiElement place) {
        return ResCompositeElementImpl.processDeclarationsDefault(
                this, processor, state, lastParent, place);
    }

    @Nullable @Override public ResType getResType(
            @Nullable ResolveState context) {
        if (context != null) return getResTypeInner(context);
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<ResType>() {
                    @Nullable @Override public Result<ResType> compute() {
                        return Result.create(getResTypeInner(null),
                                PsiModificationTracker.MODIFICATION_COUNT);
                    }
                });
    }

    @Nullable protected ResType getResTypeInner(
            @Nullable ResolveState context) {
        return findSiblingType();
    }

    @Nullable @Override public ResType findSiblingType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResType.class);
    }

    @Nullable @Override public Icon getIcon(int flags) {
        Icon icon = null;
        if (this instanceof ResPrecisModuleDecl) icon = RESOLVEIcons.PRECIS;
        else if (this instanceof ResPrecisExtensionModuleDecl) icon = RESOLVEIcons.PRECIS_EXT;
        else if (this instanceof ResConceptModuleDecl) icon = RESOLVEIcons.CONCEPT;
        else if (this instanceof ResImplModuleDecl) icon = RESOLVEIcons.IMPL;
        else if (this instanceof ResFacilityModuleDecl) icon = RESOLVEIcons.FACILITY;
        else if (this instanceof ResTypeModelDecl) icon = RESOLVEIcons.TYPE_MODEL;
        else if (this instanceof ResTypeReprDecl) icon = RESOLVEIcons.TYPE_REPR;
        else if (this instanceof ResFacilityDecl) icon = RESOLVEIcons.FACILITY;
        else if (this instanceof ResTypeParamDecl) icon = RESOLVEIcons.GENERIC_TYPE;
        else if (this instanceof ResMathVarDef) icon = RESOLVEIcons.VARIABLE;
        else if (this instanceof ResOperationDecl) icon = RESOLVEIcons.FUNCTION_DECL;
        else if (this instanceof ResOperationWithBodyNode) icon = RESOLVEIcons.FUNCTION_IMPL;
        else if (this instanceof ResParamDef) icon = RESOLVEIcons.PARAMETER;
        //TODO: complete the icon list here as you go along
        if (icon != null) {
            if ((flags & Iconable.ICON_FLAG_VISIBILITY) != 0) {
                RowIcon rowIcon =
                        ElementBase.createLayeredIcon(this, icon, flags);
                rowIcon.setIcon(isPublic() ? PlatformIcons.PUBLIC_ICON :
                        PlatformIcons.PRIVATE_ICON, 1);
                return rowIcon;
            }
            return icon;
        }
        return super.getIcon(flags);
    }
}
