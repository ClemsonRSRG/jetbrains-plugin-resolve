package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.uses.ResImportReference;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResPsiImplUtil {

    @NotNull public static String getText(@Nullable ResType o) {
        if (o == null) return "";
        boolean s = o instanceof ResRecordType;
        if (s) {
            PsiElement parent = o.getParent();
            if (parent instanceof ResAbstractTypeDecl) {
                String n = ((ResAbstractTypeDecl)parent).getName();
                String p = ((ResAbstractTypeDecl)parent).getContainingFile().getName();
                if (n != null && p != null) return p + "::" + n;
            }
            return s ? "record {...}" : "";
        }
        String text = o.getText();
        if (text == null) return "";
        return text.replaceAll("\\s+", " ");
    }

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesItem o) {
        return new ResUsesReferenceSet(o).getAllReferences();
    }

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesItem usesItem) {
        String text = usesItem.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
                TextRange.EMPTY_RANGE;
    }

    @Nullable public static ResType getGoType(@NotNull final ResExpression o,
                                              @Nullable ResolveState context) {
        return null; //TODO TODO TODO
    }

    @NotNull public static ResReference getReference(
            @NotNull ResReferenceExpression o) {
        return new ResReference(o);
    }

    @NotNull public static PsiReference getReference(
            @NotNull ResTypeReferenceExpression o) {
        return new ResTypeReference(o);
    }

    @Nullable public static ResTypeReferenceExpression getQualifier(
            @NotNull ResTypeReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExpression.class);
    }

    /** ok, in the go plugin don't be fooled by the seeming lack of connection between
     *  GoImportReferenceHelper and GoFileContextProvider -- these are responsible
     *  for setting getDefaultContext to "src/go/" etc...
     */
    @Nullable public static PsiFile resolve(@NotNull ResUsesItem usesItem) {
       PsiReference[] references = usesItem.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference = ((FileReferenceOwner)reference).getLastFileReference();
                PsiElement result = lastFileReference != null ? lastFileReference.resolve() : null;
                PsiFile x = result instanceof PsiFile ? (PsiFile)result : null;
                return x;
            }
        }
        return null;
    }
}
