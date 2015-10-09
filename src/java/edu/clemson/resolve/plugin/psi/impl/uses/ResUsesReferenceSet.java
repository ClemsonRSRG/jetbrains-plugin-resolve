package edu.clemson.resolve.plugin.psi.impl.uses;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 10/6/15.
 */
public class ResUsesReferenceSet extends FileReferenceSet {
    public ResUsesReferenceSet(String str, @NotNull PsiElement element, int startInElement, PsiReferenceProvider provider, boolean caseSensitive, boolean endingSlashNotAllowed, @Nullable FileType[] suitableFileTypes) {
        super(str, element, startInElement, provider, caseSensitive, endingSlashNotAllowed, suitableFileTypes);
    }

    public ResUsesReferenceSet(@NotNull ResUsesItem usesItem) {
        super(usesItem.getText(), usesItem, usesItem.getTextOffset(), null, true);
    }

    @Nullable @Override public PsiFileSystemItem resolve() {
        return super.resolve();
    }

    @NotNull @Override public FileReference createFileReference(TextRange range,
                                                                int index,
                                                                String text) {
        return new ResImportReference(this, range, index, text);
    }
}
