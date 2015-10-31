package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResModuleBlock;
import edu.clemson.resolve.plugin.psi.ResStatement;
import edu.clemson.resolve.plugin.psi.ResVarDeclGroupList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResAbstractModuleBlockImpl
        extends
            ResCompositeElementImpl implements ResModuleBlock {

    public ResAbstractModuleBlockImpl(ASTNode e) {
        super(e);
    }

    //Don't really currently have an example of statements at the top module
    //level, but hey, who knows.
    @Override @NotNull public List<ResStatement> getStatementList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResStatement.class);
    }

    //This is a more likely possibility.
    @Override @NotNull public ResVarDeclGroupList getVarDeclGroupList() {
        return findNotNullChildByClass(ResVarDeclGroupList.class);
    }
}
