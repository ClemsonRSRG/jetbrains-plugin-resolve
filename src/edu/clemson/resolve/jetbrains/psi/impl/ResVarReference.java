package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResVarReference extends ResCachedReference<ResVarDef> {

    protected ResVarReference(@NotNull ResVarDef element) {
        super(element);
    }

    @Nullable @Override protected PsiElement resolveInner() {
        ResVarProcessor p = new ResVarProcessor(myElement, false) {
            @Override protected boolean crossOff(@NotNull PsiElement e) {
                return super.crossOff(e);//e instanceof ResRecordFieldDef || super.condition(e);
            }
        };
        processResolveVariants(p);
        return p.getResult();
    }

    @Override public boolean processResolveVariants(
            @NotNull final ResScopeProcessor processor) {
        ResVarProcessor p = processor instanceof ResVarProcessor
                ? ((ResVarProcessor)processor)
                : new ResVarProcessor(myElement, processor.isCompletion()) {
            @Override
            public boolean execute(@NotNull PsiElement e, @NotNull ResolveState state) {
                return super.execute(e, state) && processor.execute(e, state);
            }
        };
        return false;
    }

    public static class ResVarProcessor extends ResScopeProcessorBase {

        @Nullable private final ResCompositeElement scope;

        public ResVarProcessor(@NotNull PsiElement origin, boolean completion) {
            this(origin, origin, completion, false);
        }

        public ResVarProcessor(@NotNull PsiElement requestedName,
                               @NotNull PsiElement origin, boolean completion,
                               boolean delegate) {
            super(requestedName, origin, completion);
            this.scope = getScope(origin);
        }

        @Nullable public static ResCompositeElement getScope(
                @Nullable PsiElement o) {
        /*ResWhileStatement whileStatement = PsiTreeUtil
                .getParentOfType(o, ResWhileStatement.class);
        if (whileStatement != null) return whileStatement.getBlock();
        ResIfStatement ifStatement = PsiTreeUtil
                .getParentOfType(o, ResIfStatement.class);
        if (ifStatement != null) return ifStatement.getBlock();
        ResElseStatement elseStatement = PsiTreeUtil
                .getParentOfType(o, ResElseStatement.class);
        if (elseStatement != null) return elseStatement.getBlock();
       */
            //TODO: NOT RIGHT YET.. SHOULD JUST BE A GENERAL FXN BLOCK...
            return PsiTreeUtil.getParentOfType(o, ResOpBlock.class);
        }
        @Override protected boolean crossOff(@NotNull PsiElement e) {
            return !(e instanceof ResVarDef) &&
                   !(e instanceof ResParamDef) &&
                   !(e instanceof ResFieldDef) &&
                   !(e instanceof ResTypeLikeNodeDecl) &&
                   !(e instanceof ResFacilityDecl);
        }
    }
}