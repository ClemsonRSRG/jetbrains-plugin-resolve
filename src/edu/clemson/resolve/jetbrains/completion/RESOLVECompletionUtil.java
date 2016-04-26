package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Function;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.psi.*;
import edu.clemson.resolve.jetbrains.psi.impl.ResPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVECompletionUtil {

    public static final int VAR_PRIORITY = 30;
    public static final int FUNCTION_PRIORITY = 15;
    public static final int FACILITY_PRIORITY = 5;
    public static final int DEFINITION_PRIORITY = 10;
    public static final int TYPE_PRIORITY = 20;
    public static final int KEYWORD_PRIORITY = 9;

    private static class Lazy {
        private static final SingleCharInsertHandler DIR_INSERT_HANDLER = new SingleCharInsertHandler('/');
        private static final QualifierInsertHandler FACILITY_INSERT_HANDLER =
                new QualifierInsertHandler("::", true); //TODO: it'd be nice if there were a way for the user to set padding options..
    }

    public static final InsertHandler<LookupElement> FUNCTION_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
                @Override
                public void handleInsert(InsertionContext context, LookupElement item) {
                    PsiElement element = item.getPsiElement();
                    if ( !(element instanceof ResOperationLikeNode) ) return;
                    ResOperationLikeNode elementAsOp = (ResOperationLikeNode) element;
                    int paramsCount = elementAsOp.getParamDeclList().size();
                    InsertHandler<LookupElement> handler =
                            paramsCount==0 ? ParenthesesInsertHandler.NO_PARAMETERS :
                                    ParenthesesInsertHandler.WITH_PARAMETERS;
                    handler.handleInsert(context, item);
                }
            };

    public static final LookupElementRenderer<LookupElement> VARIABLE_RENDERER =
            new LookupElementRenderer<LookupElement>() {
                @Override
                public void renderElement(LookupElement element,
                                          LookupElementPresentation p) {
                    PsiElement o = element.getPsiElement();
                    if ( !(o instanceof ResNamedElement) ) return;
                    ResNamedElement v = (ResNamedElement) o;
                    ResType type = v.getResType(null);
                    String text = ResPsiImplUtil.getText(type);
                    Icon icon = v instanceof ResMathVarDef ? RESOLVEIcons.VARIABLE :
                            v instanceof ResVarDef ? RESOLVEIcons.VARIABLE :
                                    v instanceof ResExemplarDecl ? RESOLVEIcons.EXEMPLAR :
                                            v instanceof ResParamDef ? RESOLVEIcons.PARAMETER :
                                                    v instanceof ResTypeParamDecl ? RESOLVEIcons.GENERIC_TYPE :
                                                            v instanceof ResFieldDef ? RESOLVEIcons.RECORD_FIELD : null;

                    if ( v instanceof ResMathVarDef ) {
                        //Todo: Need to write a getResTypeInner method and put it into the psi util class;
                        //should be called from ResMathVarDefImpl...
                        //typeText += v.get
                    }
                    p.setIcon(icon);
                    p.setTailText(calcTailTextForFields(v), true);
                    p.setTypeText(text);
                    p.setTypeGrayed(true);
                    p.setItemText(element.getLookupString());
                }
            };

    public static final LookupElementRenderer<LookupElement> FUNCTION_RENDERER =
            new LookupElementRenderer<LookupElement>() {
                @Override
                public void renderElement(LookupElement element,
                                          LookupElementPresentation p) {
                    PsiElement o = element.getPsiElement();
                    if ( !(o instanceof ResOperationLikeNode) ) return;
                    ResOperationLikeNode oAsOp = (ResOperationLikeNode) o;
                    String typeText = "";
                    String paramText = "";

                    paramText += "(" + StringUtil.join(oAsOp.getParamDeclList(),
                            new Function<ResParamDecl, String>() {
                                @Override
                                public String fun(ResParamDecl resParamDecl) {
                                    return resParamDecl.getText();
                                }
                            }, ", ") + ")";

                    ResType type = oAsOp.getType();
                    if ( type!=null ) typeText = type.getText();

                    p.setIcon(oAsOp.getIcon(0));
                    p.setTypeText(typeText);
                    p.setTypeGrayed(true);
                    //p.setTailText(calcTailText(f), true);
                    p.setItemText(element.getLookupString() + paramText);
                }
            };

    //TODO: Insert handler for infix defn signatures; Whitespace on lhs and rhs of name
    public static final InsertHandler<LookupElement> DEFINITION_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
                @Override
                public void handleInsert(InsertionContext context,
                                         LookupElement item) {
                    PsiElement element = item.getPsiElement();
                    if ( !(element instanceof ResMathDefinitionSignature) )
                        return;
                    ResMathDefinitionSignature signature = (ResMathDefinitionSignature) element;
                    int paramsCount = signature.getParameters().size();
                    //we don't want empty parens for nullary function applications or infix (or outfix)
                    //TODO: Actually, we could define some nice insertion handlers for outfix defns.
                    InsertHandler<LookupElement> handler =
                            paramsCount==0 //||
                                    //signature instanceof ResMathInfixDefinitionSignature ? //||
                                    //signature instanceof ResMathOutfixDefinitionSignature ?
                                    ? new BasicInsertHandler<LookupElement>() :
                                    ParenthesesInsertHandler.WITH_PARAMETERS;
                    handler.handleInsert(context, item);
                }
            };

    public static final LookupElementRenderer<LookupElement> DEFINITION_RENDERER =
            new LookupElementRenderer<LookupElement>() {
                @Override
                public void renderElement(LookupElement element,
                                          LookupElementPresentation p) {
                    PsiElement o = element.getPsiElement();
                    if ( !(o instanceof ResMathDefinitionSignature) ) return;
                    String rangeTypeText = "";
                    ResMathDefinitionSignature signature = (ResMathDefinitionSignature) o;
                    String typeText = "";

                    //Todo, move the following printing business logic into a method somewhere in ResPsiImplUtil
                    ResCompositeElement mathType = signature.getMathTypeExp();
                    boolean first = true;
                    for (ResMathVarDeclGroup grp : signature.getParameters()) {
                        if ( grp.getMathExp()!=null ) {
                            for (PsiElement e : grp.getMathVarDefList()) {
                                if ( first ) {
                                    first = false;
                                    typeText += grp.getMathExp().getText();
                                } else {
                                    typeText += " * " +
                                            grp.getMathExp().getText();
                                }
                            }
                        }
                    }
                    if ( mathType!=null ) rangeTypeText = mathType.getText();
                    if ( !typeText.equals("") ) typeText += " -> ";
                    typeText += rangeTypeText;
                    p.setIcon(RESOLVEIcons.DEF);
                    p.setTypeText(rangeTypeText);
                    p.setTypeGrayed(true);
                    // p.setTailText(calcTailText(f), true);
                    p.setItemText(element.getLookupString() + " : " + typeText);
                }
            };

    @NotNull
    public static CamelHumpMatcher createPrefixMatcher(
            @NotNull PrefixMatcher original) {
        return createPrefixMatcher(original.getPrefix());
    }

    @NotNull
    public static CamelHumpMatcher createPrefixMatcher(
            @NotNull String prefix) {
        return new CamelHumpMatcher(prefix, false);
    }

    @NotNull
    public static LookupElement createFunctionOrMethodLookupElement(
            @NotNull ResOperationLikeNode f, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> h, double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                .createWithSmartPointer(lookupString, f)
                .withRenderer(FUNCTION_RENDERER)
                .withInsertHandler(h!=null ? h : FUNCTION_INSERT_HANDLER), priority);
    }

    @Nullable
    public static LookupElement createVariableLikeLookupElement(
            @NotNull ResNamedElement v) {
        String name = v.getName();
        if ( StringUtil.isEmpty(name) ) return null;
        return createVariableLikeLookupElement(v, name, null, VAR_PRIORITY);
    }

    @NotNull
    public static LookupElement createVariableLikeLookupElement(
            @NotNull ResNamedElement v, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> insertHandler,
            double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                .createWithSmartPointer(lookupString, v)
                .withRenderer(VARIABLE_RENDERER)
                .withInsertHandler(insertHandler), priority);
    }

    @NotNull
    public static LookupElement createDefinitionLookupElement(
            @NotNull ResMathDefinitionSignature signature,
            @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> h,
            double priority) {
        return PrioritizedLookupElement.withPriority(LookupElementBuilder
                        .createWithSmartPointer(lookupString, signature)
                        .withRenderer(DEFINITION_RENDERER)
                        .withInsertHandler(h!=null ? h : DEFINITION_INSERT_HANDLER),
                priority);
    }

    @NotNull
    public static LookupElement createTypeLookupElement(
            @NotNull ResNamedElement t) {
        return createTypeLookupElement(t, StringUtil.notNullize(
                t.getName()), null, TYPE_PRIORITY);
    }

    @NotNull
    public static LookupElement createTypeLookupElement(
            @NotNull ResNamedElement t, @NotNull String lookupString,
            @Nullable InsertHandler<LookupElement> handler, double priority) {
        LookupElementBuilder builder =
                LookupElementBuilder.createWithSmartPointer(lookupString, t)
                        .withInsertHandler(handler).withIcon(t.getIcon(0));
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @Nullable
    public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility) {
        return createFacilityLookupElement(facility,
                facility.getIdentifier().getText());
    }

    @Nullable
    public static LookupElement createFacilityLookupElement(
            @NotNull ResFacilityDecl facility, @NotNull String name) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(name)
                        .withInsertHandler(Lazy.FACILITY_INSERT_HANDLER)
                        .withIcon(RESOLVEIcons.FACILITY), FACILITY_PRIORITY);
    }

    @Nullable
    private static String calcTailTextForFields(
            @NotNull ResNamedElement v) {
        String name = null;
        if ( v instanceof ResFieldDef ) {
            ResTypeLikeNodeDecl spec =
                    PsiTreeUtil.getParentOfType(v, ResTypeLikeNodeDecl.class);
            name = spec!=null ? spec.getName() : null;
        }
        return StringUtil.isNotEmpty(name) ? " " +
                UIUtil.rightArrow() + " " + name : null;
    }

    @NotNull
    public static LookupElementBuilder createDirectoryLookupElement(
            @NotNull PsiDirectory dir) {
        return LookupElementBuilder
                .createWithSmartPointer(dir.getName(), dir)
                .withIcon(RESOLVEIcons.DIRECTORY)
                .withInsertHandler(dir.getFiles().length==0 ?
                        Lazy.DIR_INSERT_HANDLER : null);
    }
}