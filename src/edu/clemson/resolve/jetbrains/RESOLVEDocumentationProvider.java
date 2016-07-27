package edu.clemson.resolve.jetbrains;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.ResNamedElement;
import edu.clemson.resolve.jetbrains.psi.ResVarDeclGroup;
import edu.clemson.resolve.jetbrains.psi.ResVarDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEDocumentationProvider extends AbstractDocumentationProvider {

    private static final Logger LOG = Logger.getInstance(RESOLVEDocumentationProvider.class);

    @NotNull
    private String getNamedElementSignature(PsiElement element, ResNamedElement context) {
        if (element instanceof ResVarDef) {
            String name = ((ResVarDef)element).getName();
            String type = "type";
            //if (element.getParent() instanceof ResVarDeclGroup) {
            //    type = ((ResVarDeclGroup) element.getParent()).getVarSpec().getType().getText();
           // }
           // String type =
        }
        return "";
    }

    @Nullable
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof ResNamedElement) {
            String result = getSignature(element, originalElement);
            if (StringUtil.isNotEmpty(result)) return result;
        }
        return super.getQuickNavigateInfo(element, originalElement);
    }
}
