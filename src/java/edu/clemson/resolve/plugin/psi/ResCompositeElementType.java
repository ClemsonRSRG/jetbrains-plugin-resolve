package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;

public class ResCompositeElementType extends IElementType {

    public ResCompositeElementType(@NotNull String debug) {
        super(debug, RESOLVELanguage.INSTANCE);
    }
}
