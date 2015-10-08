package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResReferenceExpression
        extends
            ResExpression, ResReferenceExpressionBase {

    @NotNull PsiElement getIdentifier();

    @NotNull ResReference getReference();

    @Nullable ResReferenceExpression getQualifier();
}
