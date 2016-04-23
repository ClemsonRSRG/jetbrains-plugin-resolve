package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//TODO: Implement isReferenceTo() and handleElementRename() to get
//refactoring, etc working.
public abstract class ResCachedReference<T extends PsiElement>
        extends
        PsiReferenceBase<T> {

    protected ResCachedReference(@NotNull T element) {
        super(element, TextRange.from(0, element.getTextLength()));
    }

    private static final ResolveCache.AbstractResolver<PsiReferenceBase, PsiElement> MY_RESOLVER =
            new ResolveCache.AbstractResolver<PsiReferenceBase, PsiElement>() {
                @Override
                public PsiElement resolve(@NotNull PsiReferenceBase base, boolean b) {
                    return ((ResCachedReference) base).resolveInner();
                }
            };

    @Nullable
    protected abstract PsiElement resolveInner();

    public abstract boolean processResolveVariants(
            @NotNull ResScopeProcessor processor);

    @Nullable
    @Override
    public PsiElement resolve() {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false) : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }
}
