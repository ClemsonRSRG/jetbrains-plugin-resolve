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

    @Nullable public ResModule getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModule.class);
    }

    @NotNull public List<ResTypeLikeNodeDecl> getTypes() {
        ResModule enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getTypes() :
                new ArrayList<ResTypeLikeNodeDecl>();
    }

    /*@NotNull public List<ResFacilityDecl> getFacilities() {
        ResModule enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getFacilities() :
                new ArrayList<ResFacilityDecl>();
    }*/
}
