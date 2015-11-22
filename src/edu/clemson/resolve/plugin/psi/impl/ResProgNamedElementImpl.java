package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResProgNamedElement;
import edu.clemson.resolve.plugin.psi.ResProgType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResProgNamedElementImpl
        extends
            ResNamedElementImpl implements ResProgNamedElement {

    public ResProgNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public ResProgType getResProgType(
            @Nullable ResolveState context) {
        /*if (context != null) return getResTypeInner(context);
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<ResType>() {
                    @Nullable @Override public Result<ResType> compute() {
                        return Result.create(getResTypeInner(null),
                                PsiModificationTracker.MODIFICATION_COUNT);
                    }
                });*/
        return null;
    }

    @Nullable protected ResProgType getResProgTypeInner(
            @Nullable ResolveState context) {
        return findSiblingProgType();
    }

    @Nullable @Override public ResProgType findSiblingProgType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResProgType.class);
    }
}
