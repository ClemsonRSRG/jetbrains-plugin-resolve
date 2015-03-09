package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class RESOLVETokenType extends IElementType {
    public RESOLVETokenType(@NotNull @NonNls String debugName) {
        super(debugName, RESOLVELanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "RESOLVETokenType." + super.toString();
    }
}