package edu.clemson.resolve.plugin.psi.impl;

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
    @NotNull private final String requestedName;
    protected final boolean isCompletion;

    public ResScopeProcessorBase(@NotNull String requestedName,
                                 @NotNull PsiElement origin,
                                 boolean completion) {
        this.requestedName = requestedName;
        this.origin = origin;
        this.isCompletion = completion;
    }

    @Override public boolean execute(@NotNull PsiElement psiElement,
                                     @NotNull ResolveState resolveState) {
        //TODO: Check for operation procedure decl or procedure decl here
        if (!(psiElement instanceof ResNamedElement)) return true;
        if (!isCompletion && !requestedName.equals(((ResNamedElement)psiElement).getName())) return true;
        if (condition(psiElement)) return true;
        if (psiElement.equals(origin)) return true;
        return add((ResNamedElement)psiElement) || isCompletion;
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
