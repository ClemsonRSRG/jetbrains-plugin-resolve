package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static PsiReference getReference(
            @NotNull ResTypeReferenceExpression o) {
        return new ResTypeReference(o);
    }

    @Nullable public static ResTypeReferenceExpression getQualifier(
            @NotNull ResTypeReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExpression.class);
    }

    @Nullable public static PsiReference getReference(@NotNull ResVarDef o) {
        return new ResVarReference(o);
    }

    @Nullable public static ResTypeRefNode getGoTypeInner(
            @NotNull ResExpression o, @Nullable ResolveState context) {
        /*if (o instanceof ResReferenceExpression) {
            PsiReference reference = o.getReference();
            PsiElement resolve = reference != null ? reference.resolve();
            return resolve
        }*/
        return null;
    }
}
