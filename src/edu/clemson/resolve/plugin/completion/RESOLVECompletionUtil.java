package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
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
    public static final int VAR_PRIORITY = 1;

    private RESOLVECompletionUtil() {}

    private static class Lazy {
        private static final SingleCharInsertHandler FACILITY_INSERT_HANDLER =
                new SingleCharInsertHandler('.');
    }

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
                        //v instanceof ResRecordFieldDef ? RESOLVEIcons.FIELD :
                        //v instanceof ResConstantDef ? RESOLVEIcons.CONSTANT :
                         null;

            p.setIcon(icon);
            //p.setTailText(calcTailTextForFields(v), true);
            p.setTypeText(text);
            p.setTypeGrayed(true);
            p.setItemText(element.getLookupString());
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
                        .withInsertHandler(handler).withIcon(RESOLVEIcons.FILE);
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
}
