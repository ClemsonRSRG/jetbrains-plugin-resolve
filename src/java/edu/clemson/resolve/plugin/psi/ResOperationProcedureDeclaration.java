package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ResOperationProcedureDeclaration
        extends
            ResNamedElement, ResCompositeElement {

   // @NotNull List<GoStatement> getStatementList();

    @NotNull PsiElement getIdentifier();

    @Nullable ResType getType();
}
