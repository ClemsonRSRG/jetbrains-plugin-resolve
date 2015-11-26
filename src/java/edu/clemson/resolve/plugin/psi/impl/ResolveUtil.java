package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ResolveUtil {

    public static boolean treeWalkUp(@Nullable PsiElement place,
                                     @NotNull PsiScopeProcessor processor) {
        PsiElement lastParent = null;
        PsiElement run = place;
        while (run != null) {
            if (place != run && !run.processDeclarations(processor,
                    ResolveState.initial(), lastParent, place)) {
                return false;
            }
            lastParent = run;
            run = run.getParent();
        }
        return true;
    }

    public static boolean processChildrenFromTop(@NotNull PsiElement element,
                                                 @NotNull PsiScopeProcessor processor,
                                                 @NotNull ResolveState substitutor,
                                                 @Nullable PsiElement lastParent,
                                                 @NotNull PsiElement place) {
        PsiElement run = element.getFirstChild();
        while (run != null) {
            if (run instanceof ResCompositeElement) {
                if (run.isEquivalentTo(lastParent)) return true;
                if (!run.processDeclarations(processor,
                        substitutor, null, place)) return false;
            }
            run = run.getNextSibling();
        }
        return true;
    }

    public static boolean processChildren(@NotNull PsiElement element,
                                          @NotNull PsiScopeProcessor processor,
                                          @NotNull ResolveState substitutor,
                                          @Nullable PsiElement lastParent,
                                          @NotNull PsiElement place) {
        PsiElement run = lastParent == null ? element.getLastChild() :
                lastParent.getPrevSibling();
        while (run != null) {
            if (run instanceof ResCompositeElement &&
                    !run.processDeclarations(
                            processor, substitutor, null, place)) return false;
            run = run.getPrevSibling();
        }
        return true;
    }
}
