package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.ResFacilityDecl;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 10/12/15.
 */
public class ResFacilityDeclImpl
        extends
            ResNamedElementImpl implements ResFacilityDecl {

    public ResFacilityDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstEleTypes.ID);
    }

    @NotNull @Override public ResUsesItem getSpec() {
        return findNotNullChildByClass(ResUsesItem.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResFacilityDeclImpl(node);
        }
    }
}
