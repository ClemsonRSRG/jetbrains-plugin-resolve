package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.TokenSet;
import edu.clemson.resolve.plugin.psi.RESOLVETypes;

public class RESOLVETokenKeywords {

    public static final TokenSet KEYWORDS =
            TokenSet.create(RESOLVETypes.PRECIS_MODULE,
                    RESOLVETypes.USES, RESOLVETypes.END, RESOLVETypes.PRECIS);
}
