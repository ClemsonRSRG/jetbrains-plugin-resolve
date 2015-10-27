package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesSpec importString) {
        String text = importString.getText();
        return !text.isEmpty() ? TextRange.create(1, text.length() - 1) :
                TextRange.EMPTY_RANGE;
    }

    @Nullable public static ResTypeReferenceExpression getQualifier(
            @NotNull ResTypeReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExpression.class);
    }

    @NotNull public static PsiReference getReference(
            @NotNull ResTypeReferenceExpression o) {
        return new ResTypeReference(o);
    }

    @Nullable public static PsiReference getReference(@NotNull ResVarDef o) {
        return new ResVarReference(o);
    }

    /** ok, in the go plugin don't be fooled by the seeming lack of connection between
     *  UsesReferenceHelper and the FileContextProvider -- these are responsible
     *  for setting getDefaultContext to "resolve/src/" etc...
     */
    @Nullable public static PsiFile resolve(@NotNull ResUsesSpec usesSpec) {
        PsiReference[] references = usesSpec.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference =
                        ((FileReferenceOwner)reference).getLastFileReference();
                PsiElement result = lastFileReference != null ?
                        lastFileReference.resolve() : null;
                return result instanceof PsiFile ? (PsiFile)result : null;
            }
        }
        return null;
    }

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesSpec o) {
        if (o.getTextLength() == 0) return PsiReference.EMPTY_ARRAY;
        return new ResUsesReferenceSet(o).getAllReferences();
    }

    @Nullable public static ResType getResTypeInner(
            @NotNull ResExpression o, @Nullable ResolveState context) {
        /*if (o instanceof ResReferenceExpression) {
            PsiReference reference = o.getReference();
            PsiElement resolve = reference != null ? reference.resolve();
            return resolve
        }*/
        return null;
    }

    @Nullable public static ResType getResTypeInner(
            @NotNull ResVarDef o, @Nullable ResolveState context) {
        PsiElement parent = o.getParent();

        if (parent instanceof ResVarSpec) {
            return ((ResVarSpec)parent).getType();
        }
        return null;
    }

    public static boolean processDeclarations(
            @NotNull ResCompositeElement o,
            @NotNull PsiScopeProcessor processor,
            @NotNull ResolveState state, PsiElement lastParent,
            @NotNull PsiElement place) {
        //boolean isAncestor = PsiTreeUtil.isAncestor(o, place, false);
        //if (o instanceof ResVarSpec) return isAncestor || ResCompositeElementImpl.processDeclarationsDefault(o, processor, state, lastParent, place);
        //if (isAncestor) return ResCompositeElementImpl.processDeclarationsDefault(o, processor, state, lastParent, place);

        if (o instanceof ResBlock) { //||
               // o instanceof ResIfStatement ||
               // o instanceof ResWhileStatement  {
            return processor.execute(o, state);
        }
        return ResCompositeElementImpl.processDeclarationsDefault(
                o, processor, state, lastParent, place);
    }
}
