package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.StandardPatterns.or;

public class RESOLVEKeywordCompletionContributor
        extends
            CompletionContributor implements DumbAware {

    public RESOLVEKeywordCompletionContributor() {

        extend(CompletionType.BASIC, modulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Concept", "Facility"));

        //TODO: Find a good way to combine these into a single pattern..
        extend(CompletionType.BASIC, vanillaUsesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));
        extend(CompletionType.BASIC, otherUsesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));

        extend(CompletionType.BASIC, facilityModulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "OperationProcedure", "TypeRepresentation",
                        "FacilityDeclaration"));

        extend(CompletionType.BASIC, conceptModulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "TypeFamily"));

        extend(CompletionType.BASIC, parameterModePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "evaluates", "updates", "alters", "clears",
                        "preserves", "restores", "replaces"));

        extend(CompletionType.BASIC, recordTypePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Record"));

        extend(CompletionType.BASIC, typeParamPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "type"));

        extend(CompletionType.BASIC, variablePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Var"));
    }

    private static Capture<PsiElement> typeParamPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(ResParameterMode.class)
                .inside(ResSpecModuleParameters.class);
    }

    private static Capture<PsiElement> modulePattern() {
        return onKeywordStart().withParent(ResFile.class);
    }

    private static Capture<PsiElement> vanillaUsesPattern() {
        return onKeywordStart().withParent(psiElement(ResModuleBlock.class)
                .withParent(ResModuleDecl.class))
                .isFirstAcceptedChild(psiElement());
    }

    private static Capture<PsiElement> otherUsesPattern() {
        return onKeywordStart().withParent(ResModuleDecl.class)
                .afterSibling(psiElement(ResSpecModuleParameters.class));
    }

    private static Capture<PsiElement> variablePattern() {
        return onKeywordStart().inside(psiElement(ResVarDeclGroupList.class));
    }

    private static Capture<PsiElement> recordTypePattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.TYPE_REFERENCE_EXPRESSION)
                        .withParent(psiElement()
                                .withParent(ResTypeReprDecl.class)));
    }

    private static Capture<PsiElement> parameterModePattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(ResParameterMode.class);
    }

    private static Capture<PsiElement> facilityModulePattern() {
        return topLevelModulePattern(ResFacilityModuleDecl.class,
                ResFacilityBlock.class);
    }

    private static Capture<PsiElement> conceptModulePattern() {
        return topLevelModulePattern(ResConceptModuleDecl.class,
                ResConceptBlock.class);
    }

    private static Capture<PsiElement> onKeywordStart() {
        return topLevelModulePattern(ResConceptModuleDecl.class,
                ResConceptBlock.class);
    }

    @SuppressWarnings("unchecked")
    private static Capture<PsiElement> topLevelModulePattern(
            Class<? extends ResModuleDecl> moduleType,
            Class<? extends ResModuleBlock> blockType) {
        return onKeywordStart().withParent(or(psiElement(blockType),
                            psiElement(moduleType)));
    }
}