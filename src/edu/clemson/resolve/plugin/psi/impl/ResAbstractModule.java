package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractModule
        extends
            ResNamedElementImpl implements ResModule {

    public ResAbstractModule(@NotNull ASTNode node) {
        super(node);
    }

    @Override public boolean isPublic() {
        return true;
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }

}
