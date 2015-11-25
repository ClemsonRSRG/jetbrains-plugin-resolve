package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResMathDefSig;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVECompletionUtil {

    public static final int DEFINITION_PRIORITY = 15;

    public static final InsertHandler<LookupElement> DEFINITION_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
                @Override public void handleInsert(InsertionContext context,
                                                   LookupElement item) {
                    PsiElement element = item.getPsiElement();
                    if (!(element instanceof ResMathDefSig)) return;
                    ResMathDefSig signature = (ResMathDefSig)element;
                    int paramsCount = signature.getMathVarDeclGroups().size();
                    InsertHandler<LookupElement> handler =
                            paramsCount == 0 ?
                                    ParenthesesInsertHandler.NO_PARAMETERS :
                                    ParenthesesInsertHandler.WITH_PARAMETERS;
                    handler.handleInsert(context, item);
                }
            };

    public static final LookupElementRenderer<LookupElement> DEFINITION_RENDERER =
            new LookupElementRenderer<LookupElement>() {
                @Override public void renderElement(LookupElement element,
                                                    LookupElementPresentation p) {
                    PsiElement o = element.getPsiElement();
                    if (!(o instanceof ResMathDefSig)) return;
                    String typeText = "";
                    ResMathDefSig signature = (ResMathDefSig)o;
                    String paramText = "";

                    ResMathSymbolRefExp mathType = signature.getMathType();
                    paramText = signature.getMathVarDeclGroups().toString();
                    if (mathType != null) typeText = mathType.getText();

                    p.setIcon(RESOLVEIcons.OPERATION);
                    p.setTypeText(typeText);
                    p.setTypeGrayed(true);
                    //p.setTailText(calcTailText(f), true);
                    p.setItemText(element.getLookupString() + paramText);
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
            @NotNull ResMathDefSig signature, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> h, double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                .createWithSmartPointer(lookupString, signature)
                .withRenderer(DEFINITION_RENDERER)
                .withInsertHandler(h != null ? h : DEFINITION_INSERT_HANDLER),
                priority);
    }
}
