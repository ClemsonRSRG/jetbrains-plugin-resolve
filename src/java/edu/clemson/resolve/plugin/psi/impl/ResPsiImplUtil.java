package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import edu.clemson.resolve.plugin.psi.ResRefExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResPsiImplUtil {

    @NotNull public static PsiReference getReference(
            @NotNull ResMathSymbolRefExp o) {
        return new ResMathVarLikeReference(o);
    }

    /*@Nullable public static ResRefExp getQualifier(@NotNull ResRefExp o) {
        return PsiTreeUtil.getChildOfType(o, ResRefExp.class);
    }*/

    @Nullable public static ResMathSymbolRefExp getQualifier(
            @NotNull ResMathSymbolRefExp o) {
        return PsiTreeUtil.getChildOfType(o, ResMathSymbolRefExp.class);
    }
}
