package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.ElementBase;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.RowIcon;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public abstract class ResAbstractModuleDeclImpl
        extends
            ResNamedElementImpl implements ResModuleDecl {

    public ResAbstractModuleDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull public ResModuleBlock getModuleBlock() {
        return findNotNullChildByClass(ResModuleBlock.class);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findChildByType(ResTypes.IDENTIFIER);
    }

    @NotNull @Override public List<ResUsesSpec> getUsesSpecs() {
        return getUsesList() != null ? getUsesList().getUsesSpecList() :
                ContainerUtil.<ResUsesSpec>newArrayList();
    }

    @Nullable public ResUsesList getUsesList() {
        return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
    }

    @NotNull public List<ResTypeLikeNodeDecl> getTypes() {
        final ResBlock body = this.getModuleBlock();
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResTypeLikeNodeDecl>>() {
                    @Override
                    public Result<List<ResTypeLikeNodeDecl>> compute() {
                        return Result.create(calc(ResTypeLikeNodeDecl.class),
                                ResAbstractModuleDeclImpl.this);
                    }
                });
    }

    @NotNull public List<ResFacilityDecl> getFacilities() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResFacilityDecl>>() {
                    @Override
                    public Result<List<ResFacilityDecl>> compute() {
                        return Result.create(calc(ResFacilityDecl.class),
                                ResAbstractModuleDeclImpl.this);
                    }
                });
    }

    @NotNull public List<ResOperationDecl> getOperations() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResOperationDecl>>() {
                    @Override
                    public Result<List<ResOperationDecl>> compute() {
                        return Result.create(calc(ResOperationDecl.class),
                                ResAbstractModuleDeclImpl.this);
                    }
                });
    }

    @NotNull public List<ResOperationWithBodyNode> getOperationsWithImpls() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResOperationWithBodyNode>>() {
                    @Override
                    public Result<List<ResOperationWithBodyNode>> compute() {
                        return Result.create(calc(ResOperationWithBodyNode.class),
                                ResAbstractModuleDeclImpl.this);
                    }
                });
    }

    @NotNull private <T extends ResCompositeElement> List<T> calc(
            final Class<? extends T> type) {
        final List<T> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this.getModuleBlock(),
                new Processor<PsiElement>() {
            @Override public boolean process(PsiElement e) {
                if (type.isInstance(e)) result.add(type.cast(e));
                return true;
            }
        });
        return result;
    }

    private static boolean processChildrenDummyAware(@NotNull ResBlock module,
                                                     @NotNull final Processor<PsiElement> processor) {
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
}
