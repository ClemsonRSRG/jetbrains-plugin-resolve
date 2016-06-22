package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;

//TODO: requires and ensures keyword completions would certainly be nice..

//NOTE: do a completion here (builtin) if its template can be referenced in one name. For example, Type Family is
//two names -- and you can't just use "Type" since there are technically type families and type representations and
//the IDs for the builtin "resolveHidden.xml" templates get all mixed up. So in general, the rule goes to put the
//bigger, scoped templates into "resolve.xml" (making them user configurable via the live templates dialog in settings)
//vs those that appear here which are intended to be simple, one liners, requires, ensures, etc. Patterns for these
//are inherently a little trickier. This is why this class is called KeywordCompletion.. TypeFamily isn't technically
//a valid keyword, whereas "requires", "ensures", etc are.
public class RESOLVEKeywordCompletionContributor extends CompletionContributor implements DumbAware {

    public RESOLVEKeywordCompletionContributor() {

        extend(CompletionType.BASIC, usesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));

        extend(CompletionType.BASIC, modulePattern(ResFacilityModuleDecl.class, ResFacilityBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Definition", "Implicit"));

        extend(CompletionType.BASIC, modulePattern(ResPrecisModuleDecl.class, ResPrecisBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Implicit", "Definition", "Theorem", "Corollary",
                        "Inductive", "Categorical"));

        extend(CompletionType.BASIC, modulePattern(ResPrecisExtensionModuleDecl.class, ResPrecisBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Implicit", "Definition", "Theorem", "Corollary",
                        "Inductive"));
/*
        extend(CompletionType.BASIC, modulePattern(ResImplModuleDecl.class, ResImplBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "OperationProcedure", "TypeRepresentation",
                        "FacilityDeclaration", "Procedure", "Definition"));

        extend(CompletionType.BASIC, modulePattern(ResConceptModuleDecl.class, ResConceptBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "TypeFamily",
                        "OperationDeclaration", "Definition", "Implicit",
                        "constraints"));

        extend(CompletionType.BASIC, modulePattern(ResConceptExtensionModuleDecl.class, ResConceptBlock.class),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "TypeFamily",
                        "OperationDeclaration", "Definition"));
*/
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

        extend(CompletionType.BASIC, definitionParameterPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Definition"));

        extend(CompletionType.BASIC, operationParamPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "OperationDeclaration"));

        extend(CompletionType.BASIC, statementPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "While", "If"));

        extend(CompletionType.BASIC, elseStatementPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "else"));

        extend(CompletionType.BASIC, variablePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Var"));

        /*extend(CompletionType.BASIC, mathQuantifierKeywords(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Forall", "Exists", "lambda"));*/

        extend(CompletionType.BASIC, keywordAfterSiblings(
                ResTypeReprDecl.class, psiElement(ResRecordType.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "conventions"));

        extend(CompletionType.BASIC, keywordAfterSiblings(
                ResTypeReprDecl.class,
                psiElement().andOr(psiElement(ResRecordType.class),
                        psiElement(ResConventionsClause.class))),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "correspondence"));

        extend(CompletionType.BASIC, keywordAfterSiblings(
                ResTypeReprDecl.class,
                psiElement().andOr(psiElement(ResRecordType.class),
                        psiElement(ResConventionsClause.class),
                        psiElement(ResCorrespondenceClause.class))),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "initialization_repr"));

        extend(CompletionType.BASIC, keywordAfterSiblings(
                ResTypeModelDecl.class, psiElement(ResMathReferenceExp.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "constraints"));

        extend(CompletionType.BASIC, keywordAfterSiblings(
                ResTypeModelDecl.class,
                psiElement().andOr(psiElement(ResMathReferenceExp.class),
                        psiElement(ResConstraintsClause.class))),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "initialization"));
    }

    private static Capture<PsiElement> definitionParameterPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement().withParent(psiElement(ResParamDecl.class)
                        .withParent(ResSpecModuleParameters.class)));
    }

    private static Capture<PsiElement> typeParamPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(ResParameterMode.class)
                .inside(ResSpecModuleParameters.class);
    }

    private static Capture<PsiElement> operationParamPattern() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(ResParameterMode.class)
                .inside(ResImplModuleParameters.class);
    }

    private static Capture<PsiElement> modulePattern() {
        return onKeywordStartWithParent(ResFile.class);
    }

    //if the parent is some ResBlock, whose parent in turn is a module decl then its ok to suggest a uses.
    //however: when the module is a concept impl or concept ext impl (meaning it ends with 'for' <moduleIdent>
    //the first accepted child will be that particular moduleIdent.. so the extra conditions allow this pattern
    //to accept uses clauses after moduleIdents that are part of the overall module header + module param lists.
    private static Capture<PsiElement> usesPattern() {
        return onKeywordStartWithParent(psiElement(ResBlock.class)
                .withParent(ResModuleDecl.class)
                .andOr(psiElement().isFirstAcceptedChild(psiElement()),
                        psiElement().afterSibling(psiElement(ResModuleIdentifier.class)),
                        psiElement().afterSibling(psiElement(ResModuleParameters.class))));
    }

    private static <T extends ResCompositeElement> Capture<PsiElement> keywordAfterSiblings(
            Class<T> immediateSiblingClass,
            ElementPattern<? extends PsiElement> prevSiblings) {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(PsiErrorElement.class)
                        .afterSibling(psiElement(immediateSiblingClass)
                                .withLastChild(prevSiblings)));
    }

    //TODO: if you look were we extend this, remove lambda from there and give it it's own pattern...
    private static Capture<PsiElement> mathQuantifierKeywords() {
        return psiElement(ResTypes.IDENTIFIER)
                .withParent(psiElement(ResTypes.MATH_SYMBOL_NAME)
                        .withParent(psiElement(ResTypes.MATH_REFERENCE_EXP)
                                .andOr(psiElement().isFirstAcceptedChild(psiElement()),
                                        psiElement().withParent(ResMathQuantifiedExp.class))));
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
                .withParent(psiElement(ResTypes.REFERENCE_EXP)
                        .isFirstAcceptedChild(psiElement()));
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
/*
    private static Capture<PsiElement> precisModulePattern() {
        return topLevelModulePattern(ResPrecisModuleDecl.class,
                ResPrecisBlock.class);
    }

    private static Capture<PsiElement> precisExtModulePattern() {
        return topLevelModulePattern(ResPrecisExtensionModuleDecl.class,
                ResPrecisBlock.class);
    }

    private static Capture<PsiElement> facilityModulePattern() {
        return topLevelModulePattern(ResFacilityModuleDecl.class,
                ResFacilityBlock.class);
    }

    private static Capture<PsiElement> conceptModulePattern() {
        return topLevelModulePattern(ResConceptModuleDecl.class,
                ResConceptBlock.class);
    }

    private static Capture<PsiElement> conceptExtModulePattern() {
        return topLevelModulePattern(ResConceptExtensionModuleDecl.class,
                ResConceptBlock.class);
    }

    private static Capture<PsiElement> implementationModulePattern() {
        return topLevelModulePattern(ResImplModuleDecl.class,
                ResImplBlock.class);
    }*/

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

    private static Capture<PsiElement> modulePattern(Class<? extends ResModuleDecl> moduleType,
                                                     Class<? extends ResBlock> blockType) {
        return onKeywordStartWithParent(psiElement(blockType)
                .withParent(moduleType));
    }
}