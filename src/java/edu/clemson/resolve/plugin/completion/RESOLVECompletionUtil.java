package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;
    public static final int NOT_IMPORTED_TYPE_PRIORITY = 5;
    public static final int TYPE_PRIORITY = NOT_IMPORTED_TYPE_PRIORITY + 10;

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResAbstractTypeDecl t) {
        return createTypeLookupElement(t, StringUtil.notNullize(t.getName()),
                null, null, TYPE_PRIORITY);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResAbstractTypeDecl t,
            @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler,
            @Nullable String importPath,
            double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                    .withInsertHandler(handler).withIcon(RESOLVEIcons.MODULE);
        if (importPath != null) builder =
                builder.withTailText(" " + importPath, true);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @NotNull public static LookupElementBuilder createDirectoryLookupElement(
            @NotNull PsiDirectory dir) {
        int files = dir.getFiles().length;
        return LookupElementBuilder.createWithSmartPointer(
                dir.getName(), dir).withIcon(RESOLVEIcons.MODULE);
    }

}
