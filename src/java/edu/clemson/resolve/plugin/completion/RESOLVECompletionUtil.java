package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;

    @NotNull public static LookupElement createUsesLookupElement(
            @NotNull String importPath,
            @Nullable String contextImportPath,
            boolean forType) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(importPath)
                        .withIcon(RESOLVEIcons.FILE), //todo: figure out correct icon..
                        2);
    }

}
