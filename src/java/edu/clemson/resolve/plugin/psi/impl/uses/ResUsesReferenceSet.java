package edu.clemson.resolve.plugin.psi.impl.uses;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 10/6/15.
 */
public class ResUsesReferenceSet extends FileReferenceSet {

    public ResUsesReferenceSet(@NotNull ResUsesItem usesItem) {
        super(usesItem.getText(), usesItem, usesItem.getTextOffset(), null, true);
    }

    @Nullable @Override public PsiFileSystemItem resolve() {
        if (isAbsolutePathReference()) {
            return null;
        }
        return super.resolve();
    }

    /*@NotNull @Override public FileReference createFileReference(TextRange range,
                                                                int index,
                                                                String text) {
       // return new GoImportReference(this, range, index, text);
    }*/
}
