package edu.clemson.resolve.plugin.psi.impl.scopeprocessing;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.NotNull;

public class ResTypeProcessor extends ResScopeProcessorBase {

    public ResTypeProcessor(@NotNull PsiElement origin, boolean completion) {
        super(origin, completion);
    }

    @Override protected boolean condition(@NotNull PsiElement element) {
        return true;//return !(element instanceof ResTypeSpec);
    }
}
