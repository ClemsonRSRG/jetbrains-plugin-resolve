package edu.clemson.resolve.jetbrains.template;

import com.intellij.codeInsight.template.EverywhereContextType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class RESOLVEEverywhereContextType extends RESOLVELiveTemplateContextType {

    protected RESOLVEEverywhereContextType() {
        super("RESOLVE", "RESOLVE", EverywhereContextType.class);
    }

    @Override protected boolean isInContext(@NotNull PsiElement element) {
        return !(element instanceof PsiComment);
    }
}
