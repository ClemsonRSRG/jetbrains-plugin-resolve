package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import edu.clemson.resolve.plugin.psi.NamedElement;
import edu.clemson.resolve.plugin.psi.Type;
import edu.clemson.resolve.plugin.psi.TypeReferenceExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NamedElementImpl
        extends
            ResolveCompositeElementImpl implements NamedElement {

    public NamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public boolean isPublic() {
        return true;
    }

    @Nullable @Override public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Nullable @Override public String getName() {
        PsiElement identifier = getIdentifier();
        return identifier != null ? identifier.getText() : null;
    }

    @Nullable @Override public String getQualifiedName() {
        return null;
    }

    @Nullable
    @Override
    public Type findSiblingType() {
        return null;
    }

    @Override
    public PsiElement setName(@NotNull String s) throws IncorrectOperationException {
        return null;
    }

    @Nullable
    @Override
    public TypeReferenceExpression getTypeReferenceExpression() {
        return null;
    }
}
