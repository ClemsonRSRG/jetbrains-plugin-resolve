package edu.clemson.resolve.plugin.completion;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;

    @Nullable public static String getContextImportPath(
            @Nullable PsiElement context) {
        if (context == null) return null;
        String currentPath = null;
        if (context instanceof PsiDirectory) {
            currentPath = RESOLVESdkUtil.getImportPath((PsiDirectory) context);
        }
        else {
            PsiFile file = context.getContainingFile();
            if (file instanceof ResFile) {
                currentPath = ((ResFile)file).getImportPath();
            }
        }
        return currentPath;
    }

}
