package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.StubBasedPsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import edu.clemson.resolve.plugin.stubs.ResUsesItemStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResUsesItem
        extends
            ResCompositeElement, StubBasedPsiElement<ResUsesItemStub> {

    @Nullable PsiElement getIdentifier();

    @NotNull PsiReference[] getReferences();

    @Nullable PsiDirectory resolve();
}
