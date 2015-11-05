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
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;
    public static final int TYPE_PRIORITY = 10;
    public static final int FACILITY_PRIORITY = 5;
    public static final int FUNCTION_PRIORITY = 15;
    public static final int VAR_PRIORITY = 1;

    private RESOLVECompletionUtil() {}

    private static class Lazy {
        private static final QualifierInsertHandler FACILITY_INSERT_HANDLER =
                new QualifierInsertHandler("::", true);
    }
    public static final InsertHandler<LookupElement> FUNCTION_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
        @Override
        public void handleInsert(InsertionContext context, LookupElement item) {
            PsiElement element = item.getPsiElement();
            if (!(element instanceof ResSignatureOwner)) return;
            ResSignatureOwner f = (ResSignatureOwner)element;
            ResSignature signature = f.getSignature();
            int paramsCount = signature != null ?
                    signature.getOperationLikeParameters()
                            .getParamDeclList().size() : 0;
            InsertHandler<LookupElement> handler =
                    paramsCount == 0 ? ParenthesesInsertHandler.NO_PARAMETERS :
                            ParenthesesInsertHandler.WITH_PARAMETERS;
            handler.handleInsert(context, item);
        }
    };

    public static final LookupElementRenderer<LookupElement> VARIABLE_RENDERER =
            new LookupElementRenderer<LookupElement>() {

        @Override public void renderElement(LookupElement element,
                                            LookupElementPresentation p) {
            PsiElement o = element.getPsiElement();
            if (!(o instanceof ResNamedElement)) return;
            ResNamedElement v = (ResNamedElement)o;
            ResType type = v.getResType(null);
            String text = ResPsiImplUtil.getText(type);
            Icon icon = v instanceof ResVarDef ? RESOLVEIcons.VARIABLE :
                        //v instanceof ResParamDef ? RESOLVEIcons.PARAMETER :
                        v instanceof ResFieldDef ? RESOLVEIcons.FIELD :
                        //v instanceof ResConstantDef ? RESOLVEIcons.CONSTANT :
                         null;

            p.setIcon(icon);
            p.setTailText(calcTailTextForFields(v), true);
            p.setTypeText(text);
            p.setTypeGrayed(true);
            p.setItemText(element.getLookupString());
        }
    };
    public static final LookupElementRenderer<LookupElement> FUNCTION_RENDERER =
            new LookupElementRenderer<LookupElement>() {
        @Override public void renderElement(LookupElement element,
                                            LookupElementPresentation p) {
            PsiElement o = element.getPsiElement();
            if (!(o instanceof ResNamedSignatureOwner)) return;
            ResNamedSignatureOwner f = (ResNamedSignatureOwner)o;
            String typeText = "";
            ResSignature signature = f.getSignature();
            String paramText = "";
            if (signature != null) {
                ResResult result = signature.getResult();
                paramText = signature.getOperationLikeParameters().getText();
                if (result != null) typeText = result.getType().getText();
            }

            p.setIcon(RESOLVEIcons.OPERATION);
            p.setTypeText(typeText);
            p.setTypeGrayed(true);
            p.setTailText(calcTailText(f), true);
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

    @NotNull public static LookupElement createFunctionOrMethodLookupElement(
            @NotNull ResNamedSignatureOwner f, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> h, double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                .createWithSmartPointer(lookupString, f)
                .withRenderer(FUNCTION_RENDERER)
                .withInsertHandler(h != null ? h : FUNCTION_INSERT_HANDLER), priority);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResTypeLikeNodeDecl t) {
        return createTypeLookupElement(t, StringUtil.notNullize(
                t.getName()), null, TYPE_PRIORITY);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResTypeLikeNodeDecl t, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler, double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                        .withInsertHandler(handler).withIcon(RESOLVEIcons.TYPE);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @Nullable public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility) {
        return createFacilityLookupElement(facility,
                facility.getIdentifier().getText());
    }

    @Nullable public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility, @NotNull String name) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(name)
                        .withInsertHandler(Lazy.FACILITY_INSERT_HANDLER)
                        .withIcon(RESOLVEIcons.FACILITY), FACILITY_PRIORITY);
    }

    @Nullable public static LookupElement createVariableLikeLookupElement(
            @NotNull ResNamedElement v) {
        String name = v.getName();
        if (StringUtil.isEmpty(name)) return null;
        return createVariableLikeLookupElement(v, name, null, VAR_PRIORITY);
    }

    @NotNull public static LookupElement createVariableLikeLookupElement(
            @NotNull ResNamedElement v, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> insertHandler,
            double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                .createWithSmartPointer(lookupString, v)
                .withRenderer(VARIABLE_RENDERER)
                .withInsertHandler(insertHandler), priority);
    }

    @Nullable private static String calcTailTextForFields(
            @NotNull ResNamedElement v) {
        String name = null;
        if (v instanceof ResFieldDef) {
            ResTypeReprDecl spec =
                    PsiTreeUtil.getParentOfType(v, ResTypeReprDecl.class);
            name = spec != null ? spec.getName() : null;
        }
        return StringUtil.isNotEmpty(name) ? " " +
                UIUtil.rightArrow() + " " + name : null;
    }


    @Nullable private static String calcTailText(ResSignatureOwner m) {
        String text = "";
        return StringUtil.isNotEmpty(text) ? " " + UIUtil.rightArrow() +
                " " + text : null;
    }
}
