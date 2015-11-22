package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathNamedElement;
import edu.clemson.resolve.plugin.psi.ResMathType;
import edu.clemson.resolve.plugin.psi.ResProgType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResMathNamedElementImpl
        extends
            ResNamedElementImpl implements ResMathNamedElement {

    public ResMathNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable protected ResMathType getResTypeInner(
            @Nullable ResolveState context) {
        return findSiblingType();
    }

    @Nullable @Override public ResMathType findSiblingType() {
        return PsiTreeUtil.getNextSiblingOfType(this, ResMathType.class);
    }

}
