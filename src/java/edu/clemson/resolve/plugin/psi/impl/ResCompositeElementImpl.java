package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResCompositeElementImpl
            extends
                ASTWrapperPsiElement implements ResCompositeElement {

    public ResCompositeElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public String toString() {
        return getNode().getElementType().toString();
    }

    @Override public boolean processDeclarations(
            @NotNull PsiScopeProcessor processor,
            @NotNull ResolveState state,
            @Nullable PsiElement lastParent,
            @NotNull PsiElement place) {
        return processDeclarationsDefault(
                this, processor, state, lastParent, place);
    }

    public static boolean processDeclarationsDefault(
            @NotNull ResCompositeElement o,
            @NotNull PsiScopeProcessor processor,
            @NotNull ResolveState state,
            @Nullable PsiElement lastParent,
            @NotNull PsiElement place) {
        if (!o.shouldGoDeeper()) return processor.execute(o, state);
        if (!processor.execute(o, state)) return false; //this (in most cases) is where the thing will be added...
        if ((  // o instanceof ResIfStatement ||
              //  o instanceof ResWhileStatement ||
                o instanceof ResBlockImpl)
                && processor instanceof ResScopeProcessorBase) {
            if (!PsiTreeUtil.isAncestor(o, ((ResScopeProcessorBase) processor).origin, false)) return true;
        }
        return o instanceof ResBlockImpl ?
                ResolveUtil.processChildrenFromTop(o, processor, state, lastParent, place) :
                ResolveUtil.processChildren(o, processor, state, lastParent, place);
    }

    @Override public boolean shouldGoDeeper() {
        return true;
    }
}
