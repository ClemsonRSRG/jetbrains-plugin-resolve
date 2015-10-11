package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResVarDefinition;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import edu.clemson.resolve.plugin.psi.impl.ResPsiImplUtil;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVECompletionUtil {

    public static final int KEYWORD_PRIORITY = 20;
    public static final int NOT_IMPORTED_TYPE_PRIORITY = 5;
    public static final int TYPE_PRIORITY = NOT_IMPORTED_TYPE_PRIORITY + 10;
    public static final int NOT_IMPORTED_VAR_PRIORITY = 5;
    public static final int VAR_PRIORITY = NOT_IMPORTED_VAR_PRIORITY + 10;

    public static final LookupElementRenderer<LookupElement> VARIABLE_RENDERER = new LookupElementRenderer<LookupElement>() {
        @Override public void renderElement(LookupElement element,
                                            LookupElementPresentation p) {
            PsiElement o = element.getPsiElement();
            if (!(o instanceof ResNamedElement)) return;
            ResNamedElement v = (ResNamedElement)o;
            ResType type = v.getResType(null);
            String text = ResPsiImplUtil.getText(type);
            Icon icon = v instanceof ResVarDefinition ? RESOLVEIcons.VARIABLE :
                    /*v instanceof GoParamDefinition ? GoIcons.PARAMETER :
                            v instanceof GoFieldDefinition ? GoIcons.FIELD :
                                    v instanceof GoReceiver ? GoIcons.RECEIVER :
                                            v instanceof GoConstDefinition ? GoIcons.CONSTANT :
                                                    v instanceof GoAnonymousFieldDefinition ? GoIcons.FIELD :*/
                                                            null;

            p.setIcon(icon);
            p.setTailText(calcTailTextForFields(v), true);
            p.setTypeText(text);
            p.setTypeGrayed(true);
            p.setItemText(element.getLookupString());
        }
    };

    @Nullable private static String calcTailTextForFields(
            @NotNull ResNamedElement v) {
        String name = null;
       /* if (v instanceof ResFieldDefinition) {
            ResFieldDefinitionStub stub = ((ResFieldDefinition)v).getStub();
            ResAbstractNamedType spec = stub != null ? stub.getParentStubOfType(ResAbstractNamedType.class) : PsiTreeUtil.getParentOfType(v, ResAbstractNamedType.class);
            name = spec != null ? spec.getName() : null;
        }*/
        return StringUtil.isNotEmpty(name) ? " " + UIUtil.rightArrow() + " " + name : null;
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResAbstractTypeDecl t) {
        return createTypeLookupElement(t, StringUtil.notNullize(t.getName()),
                null, null, TYPE_PRIORITY);
    }

    @NotNull public static LookupElement createTypeLookupElement(
            @NotNull ResAbstractTypeDecl t,
            @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler,
            @Nullable String importPath,
            double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                    .withInsertHandler(handler).withIcon(RESOLVEIcons.REPR_TYPE);
        if (importPath != null) builder =
                builder.withTailText(" " + importPath, true);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @NotNull public static LookupElementBuilder createDirectoryLookupElement(
            @NotNull PsiDirectory dir) {
        int files = dir.getFiles().length;
        return LookupElementBuilder.createWithSmartPointer(
                dir.getName(), dir).withIcon(RESOLVEIcons.MODULE);
    }

    @Nullable public static LookupElement createVariableLikeLookupElement(@NotNull ResNamedElement v) {
        String name = v.getName();
        if (StringUtil.isEmpty(name)) return null;
        return createVariableLikeLookupElement(v, name,  //v instanceof GoFieldDefinition
                //? new SingleCharInsertHandler(':') {
           /* @Override
            public void handleInsert(@NotNull InsertionContext context, LookupElement item) {
                PsiFile file = context.getFile();
                if (!(file instanceof GoFile)) return;
                context.commitDocument();
                int offset = context.getStartOffset();
                PsiElement at = file.findElementAt(offset);
                GoCompositeElement ref = PsiTreeUtil.getParentOfType(at, GoValue.class, GoReferenceExpression.class);
                if (ref instanceof GoReferenceExpression && (((GoReferenceExpression) ref).getQualifier() != null || GoPsiImplUtil.prevDot(ref))) {
                    return;
                }
                GoValue value = PsiTreeUtil.getParentOfType(at, GoValue.class);
                if (value == null || PsiTreeUtil.getPrevSiblingOfType(value, GoKey.class) != null)
                    return;
                super.handleInsert(context, item);
            }
        } :*/ null, VAR_PRIORITY);
    }

    @NotNull public static LookupElement createVariableLikeLookupElement(
            @NotNull ResNamedElement v,
            @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> insertHandler,
            double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder.createWithSmartPointer(lookupString, v)
                .withRenderer(VARIABLE_RENDERER)
                .withInsertHandler(insertHandler), priority);
    }

}
