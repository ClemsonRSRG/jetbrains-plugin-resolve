package edu.clemson.resolve.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubTree;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResFile extends PsiFileBase {

    public ResFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RESOLVELanguage.INSTANCE);
    }

    @NotNull @Override public FileType getFileType() {
        return RESOLVEFileType.INSTANCE;
    }

    @Override public String toString() {
        return "RESOLVE file";
    }

    @NotNull @Override public PsiElement[] getChildren() {
        return super.getChildren();
    }

    @Nullable public ResModule getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModule.class);
    }

    @NotNull public List<ResUsesSpec> getUsesItems() {
        return getUsesList() != null ? getUsesList().getUsesSpecList() :
                ContainerUtil.<ResUsesSpec>newArrayList();
    }

    @Nullable public ResUsesList getUsesList() {
        if (getEnclosedModule() == null) return null;
        return PsiTreeUtil.findChildOfType(getEnclosedModule(),
                ResUsesList.class);
    }

    @NotNull public List<ResTypeLikeNodeDecl> getTypes() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResTypeLikeNodeDecl>>() {
                    @Override
                    public Result<List<ResTypeLikeNodeDecl>> compute() {
                        return Result.create(calcTypes(), ResFile.this);
                    }
                });
    }

    @NotNull public List<ResFacilityDecl> getFacilities() {
        return CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResFacilityDecl>>() {
                    @Override
                    public Result<List<ResFacilityDecl>> compute() {
                        return Result.create(calcFacilities(), ResFile.this);
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

    private static boolean processChildrenDummyAware(
            @NotNull ResFile file,
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
        }.process(file);
    }
}
