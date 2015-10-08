package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResType;
import edu.clemson.resolve.plugin.psi.ResTypeReprDecl;
import edu.clemson.resolve.plugin.stubs.ResTypeReprDeclStub;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResTypeReprDeclImpl
        extends
            ResNamedElementImpl<ResTypeReprDeclStub> implements ResTypeReprDecl {

    public ResTypeReprDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public ResType getType() {
        return findNotNullChildByClass(ResType.class);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResTypeReprDeclImpl(node);
        }
    }
}
