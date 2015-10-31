package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResOperationLikeNode
        extends
            ResNamedElement, ResNamedSignatureOwner {

    @Nullable public ResBlock getBlock();

    @NotNull public PsiElement getIdentifier();

    @Nullable public ResSignature getSignature();
}
