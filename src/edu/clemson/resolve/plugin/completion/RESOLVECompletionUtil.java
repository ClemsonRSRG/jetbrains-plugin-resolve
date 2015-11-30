package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int DEFINITION_PRIORITY = 15;

    public static final InsertHandler<LookupElement> DEFINITION_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
                @Override public void handleInsert(InsertionContext context,
                                                   LookupElement item) {
                    PsiElement element = item.getPsiElement();
                    if (!(element instanceof ResMathDefinitionSignature)) return;
                    ResMathDefinitionSignature signature = (ResMathDefinitionSignature)element;
                    int paramsCount = signature.getParameters().size();
                    //we don't want empty parens for nullary function applications
                    InsertHandler<LookupElement> handler =
                            paramsCount == 0 ?
                                    new BasicInsertHandler<LookupElement>() :
                                    ParenthesesInsertHandler.WITH_PARAMETERS;
                    handler.handleInsert(context, item);
                }
            };

    public static final LookupElementRenderer<LookupElement> DEFINITION_RENDERER =
            new LookupElementRenderer<LookupElement>() {
                @Override public void renderElement(LookupElement element,
                                                    LookupElementPresentation p) {
                    PsiElement o = element.getPsiElement();
                    if (!(o instanceof ResMathDefinitionSignature)) return;
                    String rangeTypeText = "";
                    ResMathDefinitionSignature signature = (ResMathDefinitionSignature)o;
                    String typeText = "";

                    ResCompositeElement mathType = signature.getMathTypeExp();
                    boolean first = true;
                    for (ResMathVarDeclGroup grp : signature.getParameters()) {
                        if (grp.getMathExp() != null) {
                            if (first) {
                                first = false;
                                typeText += grp.getMathExp().getText();
                            }
                            else {
                                typeText +=  " * " +
                                        grp.getMathExp().getText();
                            }
                        }
                    }
                    if (mathType != null) rangeTypeText = mathType.getText();
                    if (!typeText.equals("")) typeText += " -> ";
                    typeText += rangeTypeText;
                    p.setIcon(RESOLVEIcons.DEF);
                    p.setTypeText(rangeTypeText);
                    p.setTypeGrayed(true);
                    // p.setTailText(calcTailText(f), true);
                    p.setItemText(element.getLookupString() + " : " + typeText);
                }
            };

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull PrefixMatcher original) {
        return createPrefixMatcher(original.getPrefix());
    }

    @NotNull public static CamelHumpMatcher createPrefixMatcher(
            @NotNull String prefix) {
        return new CamelHumpMatcher(prefix, false);
    }

    @NotNull public static LookupElement createDefinitionLookupElement(
            @NotNull ResMathDefinitionSignature signature,
            @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> h,
            double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                        .createWithSmartPointer(lookupString, signature)
                        .withRenderer(DEFINITION_RENDERER)
                        .withInsertHandler(h != null ? h : DEFINITION_INSERT_HANDLER),
                priority);
    }
}