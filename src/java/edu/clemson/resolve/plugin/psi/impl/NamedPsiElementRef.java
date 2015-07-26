package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResolveFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class NamedPsiElementRef
        extends
            PsiReferenceBase<AbstractNamedElementRefNode> {

    String referenceName;
    public NamedPsiElementRef(AbstractNamedElementRefNode element,
                              String referenceName) {
        super(element, new TextRange(0, referenceName.length()));
        this.referenceName = referenceName;
    }

    @Nullable @Override public PsiElement resolve() {

        return MyPsiUtils.findDeclNodeAbove(getElement(), referenceName);
    }

    //See processResolveVariants(..) in go plugin (com.goide.psi.impl.GoReference.java)
   /* public boolean processResolveVariants() {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResolveFile)) return false;
        getElement().getQualifier();
    }*/

    @NotNull @Override public Object[] getVariants() {
        String prefix = myElement.getText();
        ModuleBlockNode bodyElements =
                PsiTreeUtil.getContextOfType(myElement, ModuleBlockNode.class);
        // find all rule defs (token, parser)
        Collection<? extends AbstractNamedElementRefNode> namedElementNodes =
                PsiTreeUtil.findChildrenOfAnyType(bodyElements,
                        new Class[] {TypeModelDeclNode.class}
                );

        return namedElementNodes.toArray();
    }
}
