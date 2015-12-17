package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;

public class ResTokenType extends IElementType {

    public ResTokenType(String debugName) {
        super(debugName, RESOLVELanguage.INSTANCE);
    }
}
