package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResTypeReprDecl
        extends
            ResNamedElement, StubBasedPsiElement {

    @NotNull ResType getType();

    @NotNull PsiElement getIdentifier();
}
