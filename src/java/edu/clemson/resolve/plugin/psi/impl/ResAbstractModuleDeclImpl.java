package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.psi.*;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ResAbstractModuleDeclImpl
        extends
            ResNamedElementImpl implements ResModuleDecl {

    public ResAbstractModuleDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findChildByType(ResJetbrainTypes.ID);
    }

    @Nullable @Override public ResBlockImpl getBlock() {
        return findChildByClass(ResBlockImpl.class);
    }

    @NotNull @Override public List<ResMathDefDecl> getMathDefDecls() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResMathDefDecl>>() {
                    @Override
                    public Result<List<ResMathDefDecl>> compute() {
                        return Result.create(calc(ResMathDefDecl.class),
                                ResAbstractModuleDeclImpl.this);
                    }
                });
    }

    @NotNull @Override public List<ResMathDefSig> getMathDefSigs() {
        List<ResMathDefSig> signatures = new ArrayList<ResMathDefSig>();
        for (ResMathDefDecl def : getMathDefDecls()) {
            signatures.addAll(def.getMathDefSignatures());
        }
        return signatures;
    }

    @NotNull private <T extends ResCompositeElement> List<T> calc(
            final Class<? extends T> type) {
        final List<T> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this.getBlock(), new Processor<PsiElement>() {
                    @Override public boolean process(PsiElement e) {
                        if (type.isInstance(e)) result.add(type.cast(e));
                        return true;
                    }
                });
        return result;
    }

    private static boolean processChildrenDummyAware(@Nullable ResBlockImpl module,
                                                     @NotNull final Processor<PsiElement> processor) {
        if (module == null) return false;
        return new Processor<PsiElement>() {
            @Override
            public boolean process(@NotNull PsiElement psiElement) {
                for (PsiElement child = psiElement.getFirstChild();
                     child != null; child = child.getNextSibling()) {
                    if (child instanceof GeneratedParserUtilBase.DummyBlock) {
                        if (!process(child)) return false;
                    }
                    else if (!processor.process(child)) return false;
                }
                return true;
            }
        }.process(module);
    }

    public static class Factory implements PsiElementFactory {
        public static Factory INSTANCE = new Factory();

        @Override public PsiElement createElement(ASTNode node) {
            return new ResPrecisModuleDeclImpl(node);
        }
    }
}
