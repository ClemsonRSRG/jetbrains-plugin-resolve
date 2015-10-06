package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiFilePattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.impl.*;
import org.antlr.intellij.adaptor.lexer.RuleElementType;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;
import static com.intellij.patterns.StandardPatterns.or;

public class RESOLVEKeywordCompletionContributor extends CompletionContributor {

    //TODO: Put these someplace (more) sensible
    protected static TokenElementType ID = RESOLVETokenTypes
            .TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID);
    protected static RuleElementType OP_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationDecl);
    protected static RuleElementType REQUIRES_CLAUSE = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_requiresClause);
    protected static RuleElementType ENSURES_CLAUSE = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_ensuresClause);
    protected static RuleElementType TYPE_MODEL_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_typeModelDecl);
    protected static RuleElementType TYPE = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_type);
    protected static RuleElementType STMT = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_stmt);
    protected static RuleElementType MATH_TYPE_EXP = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_mathTypeExp);
    protected static RuleElementType OP_PROC_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationProcedureDecl);
    protected static RuleElementType OP_PARAM_LIST = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationParameterList);
    protected static RuleElementType SPEC_PARAM_LIST = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_specModuleParameterList);
    protected static RuleElementType SPEC_PARAM_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_specModuleParameterDecl);
    protected static RuleElementType PARAM_DECL_GRP = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_parameterDeclGroup);

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, topLevelPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Precis", "Implementation", "Enhancement", "Concept", "Facility"));

       /* extend(CompletionType.BASIC, constraintPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "constraints"));*/

      /*  extend(CompletionType.BASIC, opParameterModePattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "evaluates", "updates", "alters", "replaces", "preserves", "restores", "clears"));
        extend(CompletionType.BASIC, requiresOpProcPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "requires"));

        extend(CompletionType.BASIC, requiresPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "requires"));

        extend(CompletionType.BASIC, ensuresPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "ensures"));

        extend(CompletionType.BASIC, ensuresOpProcPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "ensures"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(ConceptModule.class),
                        psiElement(EnhancementModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "ResType Family", "Operation"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(FacilityModule.class),
                        psiElement(ConceptImplModule.class),
                        psiElement(EnhancementImplModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Operation Procedure", "Facility Decl", "ResType Record Rep", "ResType Rep"));

        //need this extension since
        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(ConceptImplModule.class),
                        psiElement(EnhancementImplModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Procedure"));
*/
        extend(CompletionType.BASIC, usesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));

        extend(CompletionType.BASIC, variableSectionPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Var"));
    }

    private static PsiElementPattern.Capture<PsiElement> topLevelPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(psiElement(ASTWrapperPsiElement.class)
                                .withParent(resolveFile())));
    }

    private static PsiElementPattern.Capture<PsiElement> moduleBodyPattern(
            PsiElementPattern.Capture<?> ... e) {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(psiElement().withParent(psiElement()
                                .andOr(e))));
    }

    //Todo: This is a bit closer for vars.. but this still allows stmts and vars to be interleaved
    // --which shouldn't be permitted.
    private static PsiElementPattern.Capture<PsiElement> variableSectionPattern() {
        return psiElement(ID).withParent(psiElement()
                .withParent(psiElement(OP_PROC_DECL)));
    }

    private static PsiElementPattern.Capture<PsiElement> usesPattern() {
        return psiElement(ID).withParent(psiElement(PsiErrorElement.class)
                .withParent(psiElement().withParent(ResAbstractModule.class)
                        .isFirstAcceptedChild(psiElement())));
    }

   /* private static PsiElementPattern.Capture<PsiElement> constraintPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(ModuleBlockNode.class)
                        .afterSibling(psiElement(TYPE_MODEL_DECL)
                                .withLastChild(psiElement(MATH_TYPE_EXP))));
    }

    private static PsiElementPattern.Capture<PsiElement> requiresOpProcPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(OperationProcedureDeclImpl.class)
                        .afterSibling(or(psiElement(OP_PARAM_LIST), psiElement(TYPE))));
    }

    private static PsiElementPattern.Capture<PsiElement> ensuresOpProcPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class)
                        .withParent(OperationProcedureDeclImpl.class)
                        .afterSibling(or(psiElement(OP_PARAM_LIST),
                                psiElement(REQUIRES_CLAUSE), psiElement(TYPE))));
    }

    private static PsiElementPattern.Capture<PsiElement> requiresPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(ModuleBlockNode.class)
                        .afterSibling(psiElement(OP_DECL).withLastChild(
                                or(psiElement(OP_PARAM_LIST), psiElement(TYPE)))));
    }

    private static PsiElementPattern.Capture<PsiElement> ensuresPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(psiElement(ModuleBlockNode.class))
                                .afterSibling(psiElement(OP_DECL)
                                        .withLastChild(or(psiElement(REQUIRES_CLAUSE),
                                                psiElement(OP_PARAM_LIST), psiElement(TYPE)))));
    }

    private static PsiElementPattern.Capture<PsiElement> opParameterModePattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(or(
                        psiElement(OP_PARAM_LIST),
                        psiElement(SPEC_PARAM_LIST),
                        psiElement(SPEC_PARAM_DECL).isFirstAcceptedChild(psiElement()),
                        psiElement(SPEC_PARAM_DECL),
                        psiElement(PARAM_DECL_GRP))));
    }*/

    private static PsiFilePattern.Capture<ResFile> resolveFile() {
        return psiFile(ResFile.class);
    }
}
