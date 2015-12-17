package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;

/** * An Intellij specific token type for any {@link ResCompositeElement}. */
public class ResCompositeElementType extends IElementType {

    public ResCompositeElementType(@NotNull String debug) {
        super(debug, RESOLVELanguage.INSTANCE);
    }

}
