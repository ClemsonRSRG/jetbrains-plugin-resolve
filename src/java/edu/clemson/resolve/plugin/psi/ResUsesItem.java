package edu.clemson.resolve.plugin.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import edu.clemson.resolve.plugin.stubs.ResUsesItemStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResUsesItem
        extends
            ResNamedElement, StubBasedPsiElement<ResUsesItemStub> {

    @Nullable PsiElement getIdentifier();

    @NotNull PsiReference[] getReferences();

    @Nullable PsiFile resolve();

    @NotNull TextRange getUsesTextRange();
}
