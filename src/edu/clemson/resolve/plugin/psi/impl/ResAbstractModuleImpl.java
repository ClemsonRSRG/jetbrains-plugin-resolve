package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ResAbstractModuleImpl
        extends
            ResNamedElementImpl implements ResModule {

    public ResAbstractModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public boolean isPublic() {
        return true;
    }

    @NotNull public List<ResUsesSpec> getUsesSpecs() {
        return getUsesList() != null ? getUsesList().getUsesSpecList() :
                ContainerUtil.<ResUsesSpec>newArrayList();
    }

    @Nullable public ResUsesList getUsesList() {
        return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }

    @NotNull public List<ResTypeLikeNodeDecl> getTypes() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResTypeLikeNodeDecl>>() {
                    @Override
                    public Result<List<ResTypeLikeNodeDecl>> compute() {
                        return Result.create(calcTypes(), ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull public List<ResFacilityDecl> getFacilities() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResFacilityDecl>>() {
                    @Override
                    public Result<List<ResFacilityDecl>> compute() {
                        return Result.create(calcFacilities(), ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull private List<ResTypeLikeNodeDecl> calcTypes() {
        final List<ResTypeLikeNodeDecl> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this, new Processor<PsiElement>() {
            @Override
            public boolean process(PsiElement e) {
                if (e instanceof ResTypeLikeNodeDecl) {
                    result.add((ResTypeLikeNodeDecl)e);
                }
                return true;
            }
        });
        return result;
    }

    @NotNull private List<ResFacilityDecl> calcFacilities() {
        final List<ResFacilityDecl> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this, new Processor<PsiElement>() {
            @Override
            public boolean process(PsiElement e) {
                if (e instanceof ResFacilityDecl) {
                    result.add((ResFacilityDecl)e);
                }
                return true;
            }
        });
        return result;
    }

    private static boolean processChildrenDummyAware(@NotNull ResModule module,
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
