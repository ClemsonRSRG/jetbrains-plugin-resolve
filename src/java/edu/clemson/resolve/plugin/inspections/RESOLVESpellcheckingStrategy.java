package edu.clemson.resolve.plugin.inspections;

import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;
import com.intellij.psi.PsiElement;

public class RESOLVESpellcheckingStrategy extends SpellcheckingStrategy {

    @Override public boolean isMyContext(@NotNull PsiElement element) {
        return RESOLVELanguage.INSTANCE.is(element.getLanguage());
    }
}