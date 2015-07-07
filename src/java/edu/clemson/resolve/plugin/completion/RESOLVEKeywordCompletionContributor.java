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
import edu.clemson.resolve.plugin.psi.RESOLVEPsiFile;
import edu.clemson.resolve.plugin.psi.impl.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;

public class RESOLVEKeywordCompletionContributor extends CompletionContributor {

    public RESOLVEKeywordCompletionContributor() {
        extend(CompletionType.BASIC, topLevelPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Precis"));

        extend(CompletionType.BASIC, topLevelPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Implementation"));

        extend(CompletionType.BASIC, topLevelPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Concept"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(ConceptModule.class),
                        psiElement(EnhancementModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Type Family", "Operation"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(FacilityModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY,
                        "Operation Procedure", "Facility Decl"));

        extend(CompletionType.BASIC, usesPattern(),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "uses"));

        //Todo: couldn't get working
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

    private static PsiFilePattern.Capture<RESOLVEPsiFile> resolveFile() {
        return psiFile(RESOLVEPsiFile.class);
    }
    
}
