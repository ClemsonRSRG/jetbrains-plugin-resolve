package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResOperationLikeNode extends ResNamedElement {

    @NotNull public PsiElement getIdentifier();

    @Nullable public ResType getResType();

    @Nullable public ResRequiresClause getRequiresClause();

    @Nullable public ResEnsuresClause getEnsuresClause();
}
