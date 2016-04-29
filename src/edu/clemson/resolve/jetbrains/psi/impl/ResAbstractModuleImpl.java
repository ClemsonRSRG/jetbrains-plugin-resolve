package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.ResTypes;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

    ResAbstractModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    public ResBlock getBlock() {
        return findNotNullChildByClass(ResBlock.class);
    }

    /*@Nullable
    public List<ResModuleSpec> getModuleSignatureIdentifiers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleSpec.class);
    }*/

    @Nullable
    @Override
    public PsiElement getIdentifier() {
        return findChildByType(ResTypes.IDENTIFIER);
    }

   /* @NotNull
    public List<ResModuleSpec> getSuperModuleSpecList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleSpec.class);
    }*/

    @Nullable
    public ResModuleParameters getModuleParameters() {
        return PsiTreeUtil.findChildOfType(this, ResModuleParameters.class);
    }

    @NotNull
    @Override
    public List<ResUsesSpecGroup> getUsesSpecGroups() {
        return getUsesList() != null ? getUsesList().getUsesSpecGroupList() :
                ContainerUtil.<ResUsesSpecGroup>newArrayList();
    }

    @Nullable
    public ResUsesList getUsesList() {
        return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
    }

    @NotNull
    public List<ResTypeParamDecl> getGenericTypeParams() {
        List<ResTypeParamDecl> genericTypes = new ArrayList<>();
        ResModuleParameters params = getModuleParameters();
        if (params instanceof ResSpecModuleParameters) {
            genericTypes.addAll(((ResSpecModuleParameters) params).getTypeParamDeclList());
        }
        return genericTypes;
    }

    @NotNull
    @Override
    public List<ResMathDefnDecl> getMathDefinitionDecls() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResMathDefnDecl>>() {
                    @Override
                    public Result<List<ResMathDefnDecl>> compute() {
                        return Result.create(calc(ResMathDefnDecl.class),
                                ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull
    @Override
    public List<ResMathDefnSig> getMathDefnSigs() {
        List<ResMathDefnSig> signatures = new ArrayList<>();
        for (ResMathDefnDecl def : getMathDefinitionDecls()) {
            signatures.addAll(def.getSignatures());
        }
        return signatures;
    }

    @NotNull
    public List<ResTypeLikeNodeDecl> getTypes() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResTypeLikeNodeDecl>>() {
                    @Override
                    public Result<List<ResTypeLikeNodeDecl>> compute() {
                        return Result.create(calc(ResTypeLikeNodeDecl.class),
                                ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull
    public List<ResFacilityDecl> getFacilities() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResFacilityDecl>>() {
                    @Override
                    public Result<List<ResFacilityDecl>> compute() {
                        return Result.create(calc(ResFacilityDecl.class),
                                ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull
    public List<ResOperationLikeNode> getOperationLikeThings() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResOperationLikeNode>>() {
                    @Override
                    public Result<List<ResOperationLikeNode>> compute() {
                        return Result.create(calc(ResOperationLikeNode.class),
                                ResAbstractModuleImpl.this);
                    }
                });
    }

    @NotNull
    private <T extends ResCompositeElement> List<T> calc(final Class<? extends T> type) {
        final List<T> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this.getBlock(), new Processor<PsiElement>() {
            @Override
            public boolean process(PsiElement e) {
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
