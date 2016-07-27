package edu.clemson.resolve.jetbrains;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RESOLVEDocumentationProvider extends AbstractDocumentationProvider {

    private static final Logger LOG = Logger.getInstance(RESOLVEDocumentationProvider.class);

    @NotNull
    private String getNamedElementSignature(PsiElement element, PsiElement context) {
        if (element instanceof ResVarDef) {
            String name = ((ResVarDef)element).getName();
            String type = "type";
            if (element.getParent() instanceof ResVarSpec) {
                ResVarSpec spec = ((ResVarSpec) element.getParent());
                if (spec.getType() != null) type = spec.getType().getText();
            }
            return "Var " + name + " : " + type;
        }
        else if (element instanceof ResOperationLikeNode) {
            String name = ((ResOperationLikeNode) element).getName();
            String params = getParametersAsString(((ResOperationLikeNode) element).getParamDeclList());
            ResType typeNode = ((ResOperationLikeNode) element).getType();
            String type = typeNode != null ? typeNode.getText() : "";
            ResRequiresClause requiresClause = ((ResOperationLikeNode) element).getRequiresClause();
            ResEnsuresClause ensuresClause = ((ResOperationLikeNode) element).getEnsuresClause();
            String result = "Operation " + name + params;
            if (!type.equals("")) result += " : " + type;
            if (requiresClause != null) result += "\n   " + requiresClause.getText();
            if (ensuresClause != null) result += "\n   " + ensuresClause.getText();
            return result;
        }
        return "";
    }

    private String getParametersAsString(@NotNull List<ResParamDecl> parameters) {
        String paramStr = "(";
        boolean first = true;

        for (ResParamDecl p : parameters) {
            if (first) {
                paramStr += p.getText();
                first = false;
            }
            else {
                paramStr += "; " + p.getText();
            }
        }
        return paramStr += ")";
    }

    @Nullable
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof ResNamedElement) {
            String result = getNamedElementSignature(element, originalElement);
            if (StringUtil.isNotEmpty(result)) return result;
        }
        return super.getQuickNavigateInfo(element, originalElement);
    }
}
