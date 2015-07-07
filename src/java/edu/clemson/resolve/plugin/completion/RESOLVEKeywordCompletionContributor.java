package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.patterns.CollectionPattern;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiFilePattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.impl.source.tree.PsiErrorElementImpl;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.RFile;
import edu.clemson.resolve.plugin.psi.impl.*;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

import java.util.Collection;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;
import static com.intellij.patterns.StandardPatterns.*;

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
                        psiElement(RConceptModule.class),
                        psiElement(REnhancementModule.class)),
                new RESOLVEKeywordCompletionProvider(
                        RESOLVECompletionUtil.KEYWORD_PRIORITY, "Type Family", "Operation"));

        extend(CompletionType.BASIC, moduleBodyPattern(
                        psiElement(RFacilityModule.class)),
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
                        .withParent(RModule.class).isFirstAcceptedChild(psiElement())));
    }

    private static PsiFilePattern.Capture<RFile> resolveFile() {
        return psiFile(RFile.class);
    }
    
}
