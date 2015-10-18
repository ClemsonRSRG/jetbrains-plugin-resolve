package edu.clemson.resolve.plugin.psi.impl.uses;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import org.jetbrains.annotations.NotNull;

public class ResUsesReferenceSet extends FileReferenceSet {

    public ResUsesReferenceSet(@NotNull ResUsesSpec usesSpec) {
        super(usesSpec.getText(), usesSpec,
                usesSpec.getTextOffset(), null, true);
    }

    @NotNull @Override public FileReference createFileReference(TextRange range,
                                                                int index,
                                                                String text) {
        return new ResUsesReference(this, range, index, text);
    }
}
