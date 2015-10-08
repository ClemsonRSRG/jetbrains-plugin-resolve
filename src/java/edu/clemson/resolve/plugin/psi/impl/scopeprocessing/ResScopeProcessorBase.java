package edu.clemson.resolve.plugin.psi.impl.scopeprocessing;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResScopeProcessorBase extends ResScopeProcessor {

    @NotNull protected final OrderedSet<ResNamedElement> myResult =
            new OrderedSet<ResNamedElement>();

    @NotNull protected final PsiElement myOrigin;
    @NotNull private final PsiElement myRequestedNameElement;
    protected final boolean myIsCompletion;

    public ResScopeProcessorBase(@NotNull PsiElement origin, boolean completion) {
        this(origin, origin, completion);
    }

    public ResScopeProcessorBase(@NotNull PsiElement requestedNameElement,
                                 @NotNull PsiElement origin,
                                 boolean completion) {
        myRequestedNameElement = requestedNameElement;
        myOrigin = origin;
        myIsCompletion = completion;
    }

    @Override public boolean execute(@NotNull PsiElement psiElement,
                                     @NotNull ResolveState resolveState) {
        if (psiElement instanceof ResOperationProcedureDeclaration) return false;
        if (!(psiElement instanceof ResNamedElement)) return true;
        String name = ((ResNamedElement)psiElement).getName();
        if (StringUtil.isEmpty(name) || !myIsCompletion && !myRequestedNameElement.textMatches(name)) return true;
        if (condition(psiElement)) return true;
        if (psiElement.equals(myOrigin)) return true;
        return add((ResNamedElement)psiElement) || myIsCompletion;
    }

    protected boolean add(@NotNull ResNamedElement psiElement) {
        return !myResult.add(psiElement);
    }

    @Nullable public ResNamedElement getResult() {
        return ContainerUtil.getFirstItem(myResult);
    }

    @NotNull public List<ResNamedElement> getVariants() {
        return myResult;
    }

    protected abstract boolean condition(@NotNull PsiElement element);
}
