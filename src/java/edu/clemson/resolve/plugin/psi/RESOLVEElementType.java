package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class RESOLVEElementType extends IElementType {
    public RESOLVEElementType(@NotNull @NonNls String debugName) {
        super(debugName, RESOLVELanguage.INSTANCE);
    }
}