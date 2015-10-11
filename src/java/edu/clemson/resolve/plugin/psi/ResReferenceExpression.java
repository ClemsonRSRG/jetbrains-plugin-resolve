package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResReferenceExpression
        extends
            ResExpression, ResReferenceExpressionBase {

    @Nullable PsiElement getIdentifier();

    @Nullable ResReference getReference();

    @Nullable ResReferenceExpression getQualifier();
}
