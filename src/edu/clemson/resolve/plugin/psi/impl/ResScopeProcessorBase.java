package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResProgNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResScopeProcessorBase extends ResScopeProcessor {
    @NotNull protected final OrderedSet<ResProgNamedElement> myResult =
            new OrderedSet<ResProgNamedElement>();

    @NotNull protected final PsiElement origin;
    @NotNull private final PsiElement requestedNameElement;
    protected final boolean isCompletion;

    public ResScopeProcessorBase(@NotNull PsiElement origin, boolean completion) {
        this(origin, origin, completion);
    }

    public ResScopeProcessorBase(@NotNull PsiElement requestedNameElement,
                                 @NotNull PsiElement origin,
                                 boolean completion) {
        this.requestedNameElement = requestedNameElement;
        this.origin = origin;
        this.isCompletion = completion;
    }

    @Override public boolean execute(@NotNull PsiElement psiElement,
                                     @NotNull ResolveState resolveState) {
        if (!(psiElement instanceof ResProgNamedElement)) return true;
        String name = ((ResProgNamedElement)psiElement).getName();
        if (StringUtil.isEmpty(name) || !isCompletion &&
                !requestedNameElement.textMatches(name)) return true;
        if (condition(psiElement)) return true;
        if (psiElement.equals(origin)) return true;
        return add((ResProgNamedElement)psiElement) || isCompletion;
    }

    protected boolean add(@NotNull ResProgNamedElement psiElement) {
        return !myResult.add(psiElement);
    }

    @Nullable public ResProgNamedElement getResult() {
        return ContainerUtil.getFirstItem(myResult);
    }

    @NotNull public List<ResProgNamedElement> getVariants() {
        return myResult;
    }

    protected abstract boolean condition(@NotNull PsiElement element);
}
