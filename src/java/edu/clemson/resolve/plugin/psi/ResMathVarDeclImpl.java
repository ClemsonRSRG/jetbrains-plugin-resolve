package edu.clemson.resolve.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResMathVarDeclImpl
        extends
            ResCompositeElementImpl implements ResMathVarDecl {

    public ResMathVarDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathVarDef> getMathVarDefList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathVarDef.class);
    }
}
