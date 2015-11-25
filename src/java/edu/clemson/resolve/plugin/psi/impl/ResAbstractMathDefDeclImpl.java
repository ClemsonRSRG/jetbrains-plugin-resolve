package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResMathDefDecl;
import edu.clemson.resolve.plugin.psi.ResMathDefSig;
import edu.clemson.resolve.plugin.psi.impl.ResCompositeElementImpl;
import edu.clemson.resolve.plugin.psi.impl.ResNamedElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResAbstractMathDefDeclImpl
        extends
            ResCompositeElementImpl implements ResMathDefDecl {

    public ResAbstractMathDefDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

}
