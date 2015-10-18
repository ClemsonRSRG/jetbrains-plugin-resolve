package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResOperationLikeNode
        extends
            ResTopLevelModuleDecl, ResNamedElement {

    @NotNull PsiElement getIdentifier();
}
