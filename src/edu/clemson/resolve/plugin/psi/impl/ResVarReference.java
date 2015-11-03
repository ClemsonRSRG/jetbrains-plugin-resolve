package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResVarDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarReference extends ResCachedReference<ResVarDef> {

    protected ResVarReference(@NotNull ResVarDef element) {
        super(element);
    }

    @Nullable @Override protected PsiElement resolveInner() {
        ResVarProcessor p = new ResVarProcessor(myElement, false) {
            @Override
            protected boolean condition(@NotNull PsiElement e) {
                return super.condition(e);//e instanceof ResRecordFieldDef || super.condition(e);
            }
        };
        processResolveVariants(p);
        return p.getResult();
    }

    @Override public boolean processResolveVariants(
            @NotNull final ResScopeProcessor processor) {
        ResVarProcessor p = processor instanceof ResVarProcessor
                ? ((ResVarProcessor)processor)
                : new ResVarProcessor(myElement, processor.isCompletion()) {
            @Override
            public boolean execute(@NotNull PsiElement e, @NotNull ResolveState state) {
                return super.execute(e, state) && processor.execute(e, state);
            }
        };
        return false;
    }
}
