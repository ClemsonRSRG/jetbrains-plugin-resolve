package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import org.jetbrains.annotations.NotNull;

/**
 * Created by daniel on 11/24/15.
 */
public class ResPsiImplUtil {

    @NotNull public static PsiReference getReference(
            @NotNull ResMathSymbolRefExp o) {
        return new ResMathVarLikeReference(o);
    }
}
