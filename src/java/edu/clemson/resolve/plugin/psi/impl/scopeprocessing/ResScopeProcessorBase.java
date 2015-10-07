package edu.clemson.resolve.plugin.psi.impl.scopeprocessing;

import com.intellij.psi.PsiElement;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
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
