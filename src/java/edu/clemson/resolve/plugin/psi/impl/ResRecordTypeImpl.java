package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResRecordType;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResVarDeclGroup;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResRecordTypeImpl
        extends
            AbstractResTypeImpl implements ResRecordType {

    public ResRecordTypeImpl(ASTNode node) {
        super(node);
    }

    @Override @NotNull public List<ResVarDeclGroup> getVariableDeclGroups() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResVarDeclGroup.class);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResRecordTypeImpl(node);
        }
    }
}
