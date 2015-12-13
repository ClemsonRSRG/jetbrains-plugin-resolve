package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;

/** * An Intellij specific token type for any {@link ResCompositeElement}. */
public class ResCompositeElementType extends IElementType {

    public ResCompositeElementType(@NotNull String debug) {
        super(debug, RESOLVELanguage.INSTANCE);
    }

}
