package edu.clemson.resolve.plugin.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiUtilCore;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RESOLVELiveTemplateContextType
        extends
            TemplateContextType {

    protected RESOLVELiveTemplateContextType(@NotNull @NonNls String id,
                                             @NotNull String presentableName,
                                             @Nullable Class<? extends TemplateContextType> baseContextType) {
        super(id, presentableName, baseContextType);
    }

    public boolean isInContext(@NotNull PsiFile file, int offset) {
        if (PsiUtilCore.getLanguageAtOffset(file, offset)
                .isKindOf(RESOLVELanguage.INSTANCE)) {
            PsiElement element = getFirstCompositeElement(file.findElementAt(offset));
            return element != null && isInContext(element);
        }
        return false;
    }

    @Nullable private static PsiElement getFirstCompositeElement(
            @Nullable PsiElement at) {
        if (at instanceof PsiComment || at instanceof LeafPsiElement &&
                ((LeafPsiElement)at).getElementType() == ResTypes.STRING) {
            return at;
        }
        PsiElement result = at;
        while (result != null && (result instanceof PsiWhiteSpace ||
                result.getChildren().length == 0)) {
            result = result.getParent();
        }
        return result;
    }

    protected abstract boolean isInContext(@NotNull PsiElement element);

    public static class RESOLVEFileContextType
            extends
                RESOLVELiveTemplateContextType {
        protected RESOLVEFileContextType() {
            super("RESOLVE_FILE", "Resolve file",
                    RESOLVEEverywhereContextType.class);
        }

        @Override protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent() instanceof ResFile;
        }
    }
}
