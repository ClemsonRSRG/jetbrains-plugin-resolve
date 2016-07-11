package edu.clemson.resolve.jetbrains.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiUtilCore;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlighter;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RESOLVELiveTemplateContextType extends TemplateContextType {

    protected RESOLVELiveTemplateContextType(@NotNull @NonNls String id,
                                             @NotNull String presentableName,
                                             @Nullable Class<? extends TemplateContextType> baseContextType) {
        super(id, presentableName, baseContextType);
    }

    public boolean isInContext(@NotNull PsiFile file, int offset) {
        if (PsiUtilCore.getLanguageAtOffset(file, offset).isKindOf(RESOLVELanguage.INSTANCE)) {
            PsiElement element = getFirstCompositeElement(file.findElementAt(offset));
            return element != null && isInContext(element);
        }
        return false;
    }

    @Nullable
    private static PsiElement getFirstCompositeElement(@Nullable PsiElement at) {
        if (at instanceof PsiComment || at instanceof LeafPsiElement &&
                ((LeafPsiElement) at).getElementType() == ResTypes.STRING) {
            return at;
        }
        PsiElement result = at;
        while (result != null && (result instanceof PsiWhiteSpace || result.getChildren().length == 0)) {
            result = result.getParent();
        }
        return result;
    }

    protected abstract boolean isInContext(@NotNull PsiElement element);

    public SyntaxHighlighter createHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    // FILE

    public static class RESOLVEFileContextType extends RESOLVELiveTemplateContextType {

        protected RESOLVEFileContextType() {
            super("RESOLVE_FILE", "RESOLVE file", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent() instanceof ResFile && !(element instanceof ResModuleDecl);
            //if the second condition is true, it means that a module has already
            //been inserted into the document, and we shouldn't suggest another -- say, when the user is typing
            //in the name.
        }
    }

    // MODULES

    public static class RESOLVEFacilityModuleContextType extends RESOLVELiveTemplateContextType {
        protected RESOLVEFacilityModuleContextType() {
            super("RESOLVE_FACILITY_MODULE", "RESOLVE Facility module", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent().getParent() instanceof ResFacilityModuleDecl;
        }
    }

    public static class RESOLVEConceptModuleContextType extends RESOLVELiveTemplateContextType {
        protected RESOLVEConceptModuleContextType() {
            super("RESOLVE_CONCEPT_MODULE", "RESOLVE Facility module", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent().getParent() instanceof ResConceptModuleDecl;
        }
    }

    public static class RESOLVEConceptExtModuleContextType extends RESOLVELiveTemplateContextType {
        protected RESOLVEConceptExtModuleContextType() {
            super("RESOLVE_CONCEPT_EXT_MODULE", "RESOLVE extension module", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent().getParent() instanceof ResConceptExtensionModuleDecl;
        }
    }

    public static class RESOLVEImplModuleContextType extends RESOLVELiveTemplateContextType {
        protected RESOLVEImplModuleContextType() {
            super("RESOLVE_IMPL_MODULE", "RESOLVE concept (+ ext) implementation module", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element.getParent().getParent() instanceof ResImplModuleDecl;
        }
    }

    // MATH CTXs

    public static class RESOLVEMathRefContextType extends RESOLVELiveTemplateContextType {
        protected RESOLVEMathRefContextType() {
            super("RESOLVE_MATH_REF", "math reference", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element instanceof ResMathReferenceExp || (element instanceof ResMathInfixApplyExp); //for infix math exprs mostly
        }
    }

    public static class RESOLVEMathDefContextType extends RESOLVELiveTemplateContextType {

        protected RESOLVEMathDefContextType() {
            super("RESOLVE_MATH_DEF", "math definition", RESOLVEEverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(@NotNull PsiElement element) {
            return element instanceof ResMathDefnSig;
        }
    }

}
