package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.util.Conditions.oneOf;
import static com.intellij.patterns.PlatformPatterns.elementType;
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
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(ResFile.class));
    }

    private static Capture<PsiElement> vanillaUsesPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(ResModule.class));
    }

    private static Capture<PsiElement> otherUsesPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(ResModule.class))
                .afterSibling(psiElement(ResSpecModuleParameters.class));
    }

    private static Capture<PsiElement> variablePattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .inside(psiElement(ResVarDeclGroupList.class)));
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
        return topLevelModulePattern(ResFacilityModule.class,
                ResFacilityBlock.class);
    }

    private static Capture<PsiElement> conceptModulePattern() {
        return topLevelModulePattern(ResConceptModule.class,
                ResConceptBlock.class);
    }

    private static Capture<PsiElement> onKeywordStart() {
        return topLevelModulePattern(ResConceptModule.class,
                ResConceptBlock.class);
    }

    @SuppressWarnings("unchecked")
    private static Capture<PsiElement> topLevelModulePattern(
            Class<? extends ResModule> moduleType,
            Class<? extends ResModuleBlock> blockType) {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                    .withParent(or(psiElement(blockType),
                            psiElement(moduleType))));
    }
}