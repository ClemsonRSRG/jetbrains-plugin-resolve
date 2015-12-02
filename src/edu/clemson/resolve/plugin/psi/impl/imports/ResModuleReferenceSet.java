package edu.clemson.resolve.plugin.psi.impl.imports;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import edu.clemson.resolve.plugin.psi.ResModuleSpec;
import org.jetbrains.annotations.NotNull;

public class ResModuleReferenceSet extends FileReferenceSet {

    public ResModuleReferenceSet(@NotNull ResModuleSpec moduleSpec) {
        super("", moduleSpec, moduleSpec.getModuleSpecTextRange()
                .getStartOffset(), null, true);
    }

    @NotNull @Override public FileReference createFileReference(
            TextRange range, int index, String text) {
        return new ResModuleReference(this, range, index, text);
    }
}
