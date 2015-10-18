package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResVarDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarReference extends ResCachedReference<ResVarDef> {

    public ResVarReference(@NotNull ResVarDef element) {
        super(element);
    }

    @Nullable @Override public PsiElement resolveInner() {
        ResVarProcessor p = new ResVarProcessor(myElement, false) {
            @Override
            protected boolean condition(@NotNull PsiElement e) {
                //if (e instanceof GoFieldDefinition) return true;
                return super.condition(e);
            }
        };
        processResolveVariants(p);
        return p.getResult();
    }
}
