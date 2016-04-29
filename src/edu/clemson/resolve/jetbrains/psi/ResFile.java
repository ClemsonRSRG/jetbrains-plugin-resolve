package edu.clemson.resolve.jetbrains.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ResFile extends PsiFileBase {

    public ResFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RESOLVELanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return RESOLVEFileType.INSTANCE;
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return super.getChildren();
    }

    @Nullable
    public ResModuleDecl getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModuleDecl.class);
    }

    @NotNull
    public List<ResMathDefnSig> getMathDefnSignatures() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getMathDefnSigs() :
                new ArrayList<ResMathDefnSig>();
    }

    @NotNull
    public List<ResUsesSpecGroup> getUsesSpecGroups() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getUsesSpecGroups() :
                new ArrayList<ResUsesSpecGroup>();
    }

   /* @NotNull
    public List<ResModuleIdentifier> getSuperModuleModuleIdentifierList() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getSuperModuleSpecList() :
                new ArrayList<ResModuleIdentifier>();
    }*/

    @NotNull
    public List<ResTypeParamDecl> getGenericTypeParams() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return (enclosedModule != null) ? enclosedModule.getGenericTypeParams() :
                new ArrayList<ResTypeParamDecl>();
    }

    @NotNull
    public List<ResParamDecl> getConstantModuleParams() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        List<ResParamDecl> params = new ArrayList<ResParamDecl>();
        if (enclosedModule == null) return params;
        ResModuleParameters moduleParams = enclosedModule.getModuleParameters();
        if (moduleParams instanceof ResSpecModuleParameters) {
            params.addAll(((ResSpecModuleParameters) params)
                    .getParamDeclList());
        }
        return params;
    }

    @NotNull
    public List<ResTypeLikeNodeDecl> getTypes() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getTypes() :
                new ArrayList<ResTypeLikeNodeDecl>();
    }

    @NotNull
    public List<ResFacilityDecl> getFacilities() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getFacilities() :
                new ArrayList<ResFacilityDecl>();
    }

    @NotNull
    public List<ResOperationLikeNode> getOperationLikeThings() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ?
                enclosedModule.getOperationLikeThings() :
                new ArrayList<ResOperationLikeNode>();
    }

    /*@NotNull public List<ResAnnotatableOperationLikeNode> getOperationImpls() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getOperationsWithImpls() :
                new ArrayList<ResAnnotatableOperationLikeNode>();
    }*/

    @Override
    public Icon getIcon(int s) {
        if (getEnclosedModule() == null) {
            return RESOLVEIcons.FILE;
        } else {
            return getEnclosedModule().getIcon(0);
        }
    }

}
