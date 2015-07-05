package edu.clemson.resolve.plugin.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RESOLVELiveTemplateContext extends TemplateContextType {

    public RESOLVELiveTemplateContext(@NotNull @NonNls String id,
            @NotNull String presentableName,
            @Nullable Class<? extends TemplateContextType> baseContextType) {
        super(id, presentableName, baseContextType);
    }

    protected abstract boolean isInContext(@NotNull PsiFile file,
                                   @NotNull PsiElement element, int offset);

    @Override public boolean isInContext(@NotNull PsiFile file, int offset) {
        // offset is where cursor or insertion point is I guess
        if ( !PsiUtilBase.getLanguageAtOffset(file, offset)
                .isKindOf(RESOLVELanguage.INSTANCE) ) {
            return false;
        }
        if ( offset==file.getTextLength() ) { // allow at EOF
            offset--;
        }
        PsiElement element = file.findElementAt(offset);

//		String trace = DebugUtil.currentStackTrace();
//		System.out.println("isInContext: element " + element +", text="+element.getText());
//		System.out.println(trace);

        if ( element==null ) {
            return false;
        }
        return isInContext(file, element, offset);
    }
}
