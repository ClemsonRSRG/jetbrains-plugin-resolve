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
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @NotNull public List<ResAbstractTypeDecl> getTypes() {
        List<ResAbstractTypeDecl> types = CachedValuesManager.getCachedValue(this,
                new CachedValueProvider<List<ResAbstractTypeDecl>>() {
                    @Override
                    public Result<List<ResAbstractTypeDecl>> compute() {
                        return Result.create(calcTypes(), ResFile.this);
                    }
                });
        return types;
    }

    @NotNull private List<ResAbstractTypeDecl> calcTypes() {
        final List<ResAbstractTypeDecl> result = ContainerUtil.newArrayList();
        processChildrenDummyAware(this, new Processor<PsiElement>() {
            @Override
            public boolean process(PsiElement e) {
                if (e instanceof ResAbstractTypeDecl) {
                        result.add((ResAbstractTypeDecl)e);
                }
                return true;
            }
        });
        return result;
    }

    private static boolean processChildrenDummyAware(@NotNull ResFile file,
                                                     @NotNull final Processor<PsiElement> processor) {
        StubTree stubTree = file.getStubTree();
        if (stubTree != null) {
            List<StubElement<?>> plainList = stubTree.getPlainList();
            for (StubElement<?> stubElement : plainList) {
                PsiElement psi = stubElement.getPsi();
                if (!processor.process(psi)) return false;
            }
            return true;
        }
        return new Processor<PsiElement>() {
            @Override
            public boolean process(@NotNull PsiElement psiElement) {
                for (PsiElement child =
                     psiElement.getFirstChild(); child != null;
                        child = child.getNextSibling()) {
                    if (child instanceof GeneratedParserUtilBase.DummyBlock) {
                        if (!process(child)) return false;
                    }
                    else if (!processor.process(child)) return false;
                }
                return true;
            }
        }.process(file);
    }


    public boolean holdsValidExecutableModule() {
       /* boolean result = PsiTreeUtil.findChildOfType(this,
                FacilityModule.class) != null;
        Collection<OperationProcedureDeclImpl> operations = PsiTreeUtil
                .findChildrenOfType(this, OperationProcedureDeclImpl.class);
        if ( result && !operations.isEmpty() ) {
            for (OperationProcedureDeclImpl o : operations) {
                if (o.getIdentifier().getText().equals("Main") ||
                        o.getIdentifier().getText().equals("main")) {
                    return true;
                }
            }
            result = false;
        }
        return result;*/
        return false;
    }
}
