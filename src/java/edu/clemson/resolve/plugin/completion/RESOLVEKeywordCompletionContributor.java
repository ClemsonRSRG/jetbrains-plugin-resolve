package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiFilePattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.sun.org.apache.xpath.internal.operations.Mod;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.ResolveFile;
import edu.clemson.resolve.plugin.psi.impl.*;
import org.antlr.intellij.adaptor.lexer.RuleElementType;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;
import static com.intellij.patterns.StandardPatterns.or;

public class RESOLVEKeywordCompletionContributor extends CompletionContributor {

    protected static RuleElementType OP_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationDecl);
    protected static RuleElementType REQUIRES_CLAUSE = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_requiresClause);
    protected static RuleElementType TYPE_MODEL_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_typeModelDecl);
    protected static RuleElementType TYPE = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_type);
    protected static RuleElementType MATH_TYPE_EXP = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_mathTypeExp);
    protected static RuleElementType OP_PROC_DECL = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationDecl);
    protected static RuleElementType OP_PARAM_LIST = RESOLVETokenTypes
            .RULE_ELEMENT_TYPES.get(Resolve.RULE_operationParameterList);

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, topLevelPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Precis", "Implementation", "Enhancement", "Concept", "Facility"));

       /* extend(CompletionType.BASIC, constraintPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "constraints"));*/

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
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Type Family", "Operation"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(FacilityModule.class),
                        psiElement(ConceptImplModule.class),
                        psiElement(EnhancementImplModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Operation Procedure", "Facility Decl", "Type Record Rep", "Type Rep"));

        //need this extension since
        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(ConceptImplModule.class),
                        psiElement(EnhancementImplModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Procedure"));

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
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement()
                        .withParent(psiElement().withParent(psiElement(
                                RESOLVETokenTypes.RULE_ELEMENT_TYPES.get(Resolve.RULE_operationProcedureDecl)))));
    }

    private static PsiElementPattern.Capture<PsiElement> usesPattern() {
        return psiElement(RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.ID))
                .withParent(psiElement(PsiErrorElement.class).withParent(psiElement()
                        .withParent(Module.class).isFirstAcceptedChild(psiElement())));
    }

    private static PsiElementPattern.Capture<PsiElement> constraintPattern() {
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
    
    private static PsiFilePattern.Capture<ResolveFile> resolveFile() {
        return psiFile(ResolveFile.class);
    }
}
