package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
//import edu.clemson.resolve.jetbrains.psi.ResMathCartProdExp;
import edu.clemson.resolve.jetbrains.psi.ResMathDefnDecl;
import edu.clemson.resolve.jetbrains.psi.ResNamedElement;
import edu.clemson.resolve.jetbrains.psi.ResOperationLikeNode;
import edu.clemson.resolve.jetbrains.psi.ResUsesList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResScopeProcessorBase extends ResScopeProcessor {
    @NotNull
    protected final OrderedSet<ResNamedElement> myResult = new OrderedSet<>();

    @NotNull
    protected final PsiElement origin;
    @NotNull
    private final PsiElement requestedNameElement;
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

    @Override
    public boolean execute(@NotNull PsiElement psiElement,
                           @NotNull ResolveState resolveState) {
        if (psiElement instanceof ResMathDefnDecl) return false;
        if (psiElement instanceof ResOperationLikeNode) return false;
        if (psiElement instanceof ResUsesList) return false;
        //if (psiElement instanceof ResMathCartProdExp) return false;

        if (!(psiElement instanceof ResNamedElement)) return true;
        String name = ((ResNamedElement) psiElement).getName();
        if (StringUtil.isEmpty(name) || !isCompletion && !requestedNameElement.textMatches(name)) return true;
        if (crossOff(psiElement)) return true;
        if (psiElement.equals(origin)) return true;
        return add((ResNamedElement) psiElement) || isCompletion;
    }

    protected boolean add(@NotNull ResNamedElement psiElement) {
        return !myResult.add(psiElement);
    }

    @Nullable
    public ResNamedElement getResult() {
        return ContainerUtil.getFirstItem(myResult);
    }

    @NotNull
    public List<ResNamedElement> getVariants() {
        return myResult;
    }

    protected abstract boolean crossOff(@NotNull PsiElement element);
}
