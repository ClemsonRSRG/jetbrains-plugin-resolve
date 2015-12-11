package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class RESOLVEKeywordCompletionContributor
        extends
            CompletionContributor implements DumbAware {

    public RESOLVEKeywordCompletionContributor() {

        extend(CompletionType.BASIC, modulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Concept", "Facility", "Implementation"));

        extend(CompletionType.BASIC, usesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));

        extend(CompletionType.BASIC, facilityModulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "OperationProcedure", "TypeRepresentation",
                        "FacilityDeclaration"));

        extend(CompletionType.BASIC, implementationModulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "OperationProcedure", "TypeRepresentation",
                        "FacilityDeclaration"));

        extend(CompletionType.BASIC, conceptModulePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "TypeFamily",
                        "OperationDeclaration"));

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

        extend(CompletionType.BASIC, statementPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "While", "If"));

        extend(CompletionType.BASIC, elseStatementPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "else"));

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
        return onKeywordStartWithParent(ResFile.class);
    }

    private static Capture<PsiElement> usesPattern() {
        return onKeywordStartWithParent(psiElement(ResBlock.class)
                .withParent(ResModuleDecl.class)
                .andOr(psiElement().isFirstAcceptedChild(psiElement()),
                        psiElement().afterSibling(psiElement(ResModuleSpec.class)),
                        psiElement()
                                .afterSibling(psiElement(ResModuleParameters.class))));
    }

    private static Capture<PsiElement> otherUsesPattern() {
        return onKeywordStartWithParent(ResBlock.class);
    }

    private static Capture<PsiElement> statementPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.REFERENCE_EXP)
                        .withParent(psiElement(ResTypes.SIMPLE_STATEMENT)));
    }

    private static Capture<PsiElement> elseStatementPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.REFERENCE_EXP)
                        .withParent(psiElement(ResTypes.SIMPLE_STATEMENT)
                                .withParent(psiElement(ResTypes.IF_STATEMENT))));
    }

    private static Capture<PsiElement> variablePattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.REFERENCE_EXP));
    }

    private static Capture<PsiElement> recordTypePattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.TYPE_REFERENCE_EXP)
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

    private static Capture<PsiElement> implementationModulePattern() {
        return topLevelModulePattern(ResImplModuleDecl.class,
                ResImplBlock.class);
    }

    private static Capture<PsiElement> onKeywordStartWithParent(
            Class<? extends PsiElement> parentClass) {
        return onKeywordStartWithParent(psiElement(parentClass));
    }

    private static Capture<PsiElement> onKeywordStartWithParent(
            Capture<? extends PsiElement> parentPattern) {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(parentPattern));
    }

    private static Capture<PsiElement> topLevelModulePattern(
            Class<? extends ResModuleDecl> moduleType,
            Class<? extends ResBlock> blockType) {
      return onKeywordStartWithParent(psiElement(blockType)
              .withParent(moduleType));
    }
}