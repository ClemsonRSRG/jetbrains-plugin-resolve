package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResUsesListImpl extends ResCompositeElementImpl {

    public ResUsesListImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public List<ResUsesItem> getUsesItems() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResUsesItem.class);
    }

    @NotNull public ResUsesItem addUses(String usesName) {
        return ResPsiImplUtil.addImport(this, usesName);
    }
}
