package edu.clemson.resolve.jetbrains.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A interface for anything in the language resembling an operation. This
 * includes top level operation declarations, procedure implementations, and
 * private operation declarations (with impls) as would be found in a facility.
 */
public interface ResOperationLikeNode extends ResNamedElement {

    @NotNull
    public PsiElement getIdentifier();

    @Nullable
    public ResType getType();

    @NotNull
    List<ResParamDecl> getParamDeclList();

    /*@Nullable public ResRequiresClause getRequiresClause();

    @Nullable public ResEnsuresClause getEnsuresClause();*/
}
