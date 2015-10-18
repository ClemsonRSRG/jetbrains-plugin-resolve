package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesSpec usesSpec) {
        String text = usesSpec.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
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

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesSpec o) {
        if (o.getTextLength() == 0) return PsiReference.EMPTY_ARRAY;
        return new ResUsesReferenceSet(o).getAllReferences();
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

    /*@Nullable public static ResReferenceExpression getQualifier(
            @NotNull ResReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResReferenceExpression.class);
    }

    @NotNull public static ResReference getReference(
            @NotNull ResReferenceExpression o) {
        return new ResReference(o);
    }*/
}
