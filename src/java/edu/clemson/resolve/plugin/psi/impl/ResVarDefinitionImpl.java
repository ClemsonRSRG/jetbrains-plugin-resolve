package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import edu.clemson.resolve.plugin.ConstTokenTypes;
import edu.clemson.resolve.plugin.psi.ResVarDefinition;
import edu.clemson.resolve.plugin.stubs.ResVarDefinitionStub;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarDefinitionImpl
        extends
            ResNamedElementImpl<ResVarDefinitionStub> implements ResVarDefinition {
    public ResVarDefinitionImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ConstTokenTypes.ID);
    }

    @Nullable public PsiReference getReference() {
        return null;
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResVarDefinitionImpl(node);
        }
    }
}
