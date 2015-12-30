package edu.clemson.resolve.jetbrains.intention;

import com.intellij.codeInspection.IntentionAndQuickFixAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImplementOperationsFix extends IntentionAndQuickFixAction {

    private final PsiElement element;

    public ImplementOperationsFix(PsiElement aModule) {
        element = aModule;
    }

    @NotNull @Override public String getName() {
        return "Implement operations";
    }

    @NotNull @Override public String getFamilyName() {
        return "Implement operations";
    }

    @Override public void applyFix(@NotNull Project project,
                                   PsiFile file,
                                   @Nullable Editor editor) {
    }

    @Override public boolean isAvailable(@NotNull final Project project,
                                         @Nullable final Editor editor,
                                         final PsiFile file) {
        if (element == null) return false;
        if (!element.isWritable()) return false;
        return true;
    }

}
