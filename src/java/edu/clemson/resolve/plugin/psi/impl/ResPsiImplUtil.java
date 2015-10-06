package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResPsiImplUtil {

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesItem o) {
        if (o.getTextLength() == 0) return PsiReference.EMPTY_ARRAY;
        return new ResUsesReferenceSet(o).getAllReferences();
    }

    @Nullable public static PsiDirectory resolve(
            @NotNull ResUsesItem usesItem) {
        PsiReference[] references = usesItem.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference =
                        ((FileReferenceOwner)reference).getLastFileReference();
                PsiElement result = lastFileReference != null ?
                        lastFileReference.resolve() : null;
                return result instanceof PsiDirectory ? (PsiDirectory)result :
                        null;
            }
        }
        return null;
    }
}
