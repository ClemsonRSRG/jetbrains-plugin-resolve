package edu.clemson.resolve.plugin.template;

import com.intellij.codeInsight.template.EverywhereContextType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class RESOLVEGenericContext extends RESOLVELiveTemplateContext {

    public RESOLVEGenericContext() {
        super("RESOLVE", "RESOLVE", EverywhereContextType.class);
    }

    @Override protected boolean isInContext(@NotNull PsiFile file,
                                            @NotNull PsiElement element,
                                            int offset) {
        return false;
    }
}
