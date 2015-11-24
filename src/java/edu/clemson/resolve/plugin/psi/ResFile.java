package edu.clemson.resolve.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
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

    @Nullable public ResModuleDecl getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModuleDecl.class);
    }

    @NotNull public List<ResMathDefSig> getMathDefSigs() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        List<ResMathDefSig> foundDecls =  enclosedModule != null ?
                enclosedModule.getMathDefSigs() :
                new ArrayList<ResMathDefSig>();
        return foundDecls;
    }
}
