package edu.clemson.resolve.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
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

    @NotNull @Override public PsiElement[] getChildren() {
        return super.getChildren();
    }

    @Nullable public ResModuleDecl getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModuleDecl.class);
    }

    @NotNull public List<ResUsesSpec> getUsesSpecs() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getUsesSpecs() :
                new ArrayList<ResUsesSpec>();
    }

    @NotNull public List<ResTypeLikeNodeDecl> getTypes() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getTypes() :
                new ArrayList<ResTypeLikeNodeDecl>();
    }

    @NotNull public List<ResFacilityDecl> getFacilities() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getFacilities() :
                new ArrayList<ResFacilityDecl>();
    }

    @NotNull public List<ResOperationDecl> getOperations() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        List<ResOperationDecl> result = enclosedModule != null ?
                enclosedModule.getOperations() :
                new ArrayList<ResOperationDecl>();
        return result;
    }
}
