package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResScopeProcessorBase extends BaseScopeProcessor {

    @NotNull protected final OrderedSet<ResNamedElement> result =
            new OrderedSet<ResNamedElement>();

    @NotNull protected final PsiElement origin;
    @NotNull private final PsiElement requestedNameElement;
    protected final boolean isCompletion;

    public ResScopeProcessorBase(@NotNull PsiElement requestedNameElement,
                                 @NotNull PsiElement origin,
                                 boolean completion) {
        this.requestedNameElement = requestedNameElement;
        this.origin = origin;
        this.isCompletion = completion;
    }

    @Override public boolean execute(@NotNull PsiElement e,
                                     @NotNull ResolveState resolveState) {
        //TODO: Check for operation procedure decl or procedure decl here
        if (!(e instanceof ResNamedElement)) return true;
        String name = ((ResNamedElement)e).getName();
        if (StringUtil.isEmpty(name) || !isCompletion && !requestedNameElement.textMatches(name)) return true;
        if (condition(e)) return true;
        if (e.equals(origin)) return true;
        return add((ResNamedElement)e) || isCompletion;
    }

    protected boolean add(@NotNull ResNamedElement psiElement) {
        return !result.add(psiElement);
    }

    @Nullable public ResNamedElement getResult() {
        return ContainerUtil.getFirstItem(result);
    }

    @NotNull public List<ResNamedElement> getVariants() {
        return result;
    }

    protected abstract boolean condition(@NotNull PsiElement element);

}
