package edu.clemson.resolve.jetbrains.editor;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.hint.ParameterInfoComponent;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.parameterInfo.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.*;
import edu.clemson.resolve.jetbrains.psi.impl.ResLightType;
import edu.clemson.resolve.jetbrains.psi.impl.ResLightType.LightFunctionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This is resposible for providing information about an operation being called, as the user is typing the call exp.
 * It should provide (and highlight) both the name of the formal parameter we're supplying an actual for + its type
 * and parameter mode. Furthermore, this will also display the precondition and postcondition (advanced functionality
 * could even highlight the parameters within these mathematical clauses as typed)..
 *
 * @author dtwelch
 */
public class RESOLVEParameterInfoHandler implements ParameterInfoHandlerWithTabActionSupport<ResArgumentList, Object, ResExp> {

    @NotNull
    @Override
    public ResExp[] getActualParameters(@NotNull ResArgumentList o) {
        return ArrayUtil.toObjectArray(o.getExpList(), ResExp.class);
    }

    @NotNull
    @Override
    public IElementType getActualParameterDelimiterType() {
        return ResTypes.COMMA;
    }

    @NotNull
    @Override
    public IElementType getActualParametersRBraceType() {
        return ResTypes.RPAREN;
    }

    @NotNull
    @Override
    public Set<Class> getArgumentListAllowedParentClasses() {
        return ContainerUtil.newHashSet();
    }

    @NotNull
    @Override
    public Set<? extends Class> getArgListStopSearchClasses() {
        return ContainerUtil.newHashSet();
    }

    @NotNull
    @Override
    public Class<ResArgumentList> getArgumentListClass() {
        return ResArgumentList.class;
    }

    @Override
    public boolean couldShowInLookup() {
        return true;
    }

    @Nullable
    @Override
    public Object[] getParametersForLookup(LookupElement item, ParameterInfoContext context) {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Nullable
    @Override
    public Object[] getParametersForDocumentation(Object p, ParameterInfoContext context) {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Nullable
    @Override
    public ResArgumentList findElementForParameterInfo(@NotNull CreateParameterInfoContext context) {
        return getList(context);
    }

    @Nullable
    private static ResArgumentList getList(@NotNull ParameterInfoContext context) {
        PsiElement at = context.getFile().findElementAt(context.getOffset());
        return PsiTreeUtil.getParentOfType(at, ResArgumentList.class);
    }

    @Override
    public void showParameterInfo(@NotNull ResArgumentList argList, @NotNull CreateParameterInfoContext context) {
        PsiElement parent = argList.getParent();
        if (!(parent instanceof ResCallExp)) return;
        LightFunctionType type = findFunctionType(((ResCallExp)parent).getReferenceExp().getResType(null));
        if (type != null) {
            context.setItemsToShow(new Object[]{type});
            context.showHint(argList, argList.getTextRange().getStartOffset(), this);
        }
    }

    @Nullable
    @Override
    public ResArgumentList findElementForUpdatingParameterInfo(@NotNull UpdateParameterInfoContext context) {
        return getList(context);
    }

    @Override
    public void updateParameterInfo(@NotNull ResArgumentList list, @NotNull UpdateParameterInfoContext context) {
        context.setCurrentParameter(ParameterInfoUtils.getCurrentParameterIndex(list.getNode(),
                context.getOffset(), ResTypes.COMMA));
    }

    @Nullable
    @Override
    public String getParameterCloseChars() {
        return ",(";
    }

    @Override
    public boolean tracksParameterIndex() {
        return true;
    }

    @Nullable
    private static LightFunctionType findFunctionType(@Nullable ResType type) {
        if (type instanceof LightFunctionType || type == null) return (LightFunctionType)type;
        ResType base = type.getUnderlyingType();
        return base instanceof LightFunctionType ? (LightFunctionType)base : null;
    }

    @Override
    public void updateUI(@Nullable Object p, @NotNull ParameterInfoUIContext context) {
        updatePresentation(p, context);
    }

    private static String updatePresentation(@Nullable Object p, @NotNull ParameterInfoUIContext context) {
        if (p == null) {
            context.setUIComponentEnabled(false);
            return null;
        }

        ResOperationLikeNode operation = p instanceof LightFunctionType ?
                ((LightFunctionType)p).getOperationLikeNode() : null;
        if (operation == null) return null;
        List<ResParamDecl> parameters = operation.getParamDeclList();
        List<String> parametersPresentations = getParameterPresentations(parameters);

        StringBuilder builder = new StringBuilder();
        int start = 0;
        int end = 0;
        if (!parametersPresentations.isEmpty()) {
            // Figure out what particular presentation is actually selected. Take in
            // account possibility of the last variadic parameter.
            int selected = context.getCurrentParameterIndex();
            for (int i = 0; i < parametersPresentations.size(); ++i) {
                if (i != 0) {
                    builder.append(", ");
                }
                if (i == selected) {
                    start = builder.length();
                }
                builder.append(parametersPresentations.get(i));

                if (i == selected) {
                    end = builder.length();
                }
            }
            //if (operation.getRequiresClause() != null) {
            //    builder.append(operation.getRequiresClause().getText())
            //}
        }
        else {
            builder.append(CodeInsightBundle.message("parameter.info.no.parameters"));
        }
        return context.setupUIComponentPresentation(builder.toString(), start, end, false, false, false, context.getDefaultParameterColor());
    }

    private static List<String> getParameterPresentations(@NotNull List<ResParamDecl> parameters) {
        List<String> result = new ArrayList<>();
        for (ResParamDecl p : parameters) {
            for (ResParamDef e : p.getParamDefList()) {
                String type = p.getType() != null ? p.getType().getText() : "<null>";
                result.add(p.getParameterMode().getText() + " " + e.getName() + " : " + type);
            }
        }
        return result;
    }
}
