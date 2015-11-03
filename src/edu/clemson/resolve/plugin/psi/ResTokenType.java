package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;

public class ResTokenType extends IElementType {

    public ResTokenType(String debugName) {
        super(debugName, RESOLVELanguage.INSTANCE);
    }
}
