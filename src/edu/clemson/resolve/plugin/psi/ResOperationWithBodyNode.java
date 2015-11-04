package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResOperationWithBodyNode
        extends
            ResNamedElement, ResNamedSignatureOwner {

    @Nullable public ResOpBlock getOpBlock();

    @NotNull public PsiElement getIdentifier();

    @Nullable public ResSignature getSignature();
}
