package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.stubs.ResNamedStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResNamedElementImpl<T extends ResNamedStub<?>>
        extends
            ResStubbedElementImpl<T> implements ResNamedElement {

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

    @Nullable
    @Override
    public GoType getGoType(@Nullable final ResolveState context) {
        if (context != null) return getGoTypeInner(context);
        return CachedValuesManager.getCachedValue(this, new CachedValueProvider<GoType>() {
            @Nullable
            @Override
            public Result<GoType> compute() {
                return Result.create(getGoTypeInner(null), PsiModificationTracker.MODIFICATION_COUNT);
            }
        });
    }

    @Nullable
    protected ResType getGoTypeInner(@Nullable ResolveState context) {
        return findSiblingType();
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
