package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.TokenSet;
import grammars.edu.plugin.psi.RESOLVETypes;

public class RESOLVETokenTypes {

    public static final TokenSet KEYWORDS =
            TokenSet.create(RESOLVETypes.PRECIS_MODULE,
                    RESOLVETypes.USES, RESOLVETypes.END, RESOLVETypes.PRECIS);
}
