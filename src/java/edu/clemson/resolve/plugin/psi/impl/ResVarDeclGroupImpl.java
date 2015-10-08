package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResVarDeclGroup;
import edu.clemson.resolve.plugin.psi.ResVarDefinition;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by daniel on 10/8/15.
 */
public class ResVarDeclGroupImpl
        extends
            ResCompositeElementImpl implements ResVarDeclGroup {

    public ResVarDeclGroupImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResVarDefinition> getFieldDefinitionList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResVarDefinition.class);
    }

    @NotNull @Override public ResType getType() {
        return findNotNullChildByClass(ResType.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResVarDeclGroupImpl(node);
        }
    }
}
