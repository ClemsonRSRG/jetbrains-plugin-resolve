package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolutionUtil {

    public static boolean treeWalkUp(@Nullable PsiElement place,
                                     @NotNull PsiScopeProcessor processor) {
        PsiElement lastParent = null;
        PsiElement run = place;
        while (run != null) {
            if (place != run && !run.processDeclarations(processor,
                    ResolveState.initial(), lastParent, place)) return false;
            lastParent = run;
            run = run.getParent();
        }
        return true;
    }
}
