package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A interface for anything in the language resembling an operation. This
 * includes top level operation declarations, procedure implementations,
 * private operation declarations (with impls) as would be found in a facility;
 * everything.
 *
 * @author dan welch <dtw.welch@gmail.com>
 * @since 0.0.1
 */
public interface ResOperationLikeNode extends ResNamedElement {

    @NotNull public PsiElement getIdentifier();

    @Nullable public ResType getType();

    @NotNull List<ResParamDecl> getParameters();

    @Nullable public ResRequiresClause getRequiresClause();

    @Nullable public ResEnsuresClause getEnsuresClause();
}
