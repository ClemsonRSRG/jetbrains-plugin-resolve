package edu.clemson.resolve.jetbrains.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.PsiFileStubImpl;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public List<ResMathDefinitionSignature> getMathDefinitionSignatures() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ?
                enclosedModule.getMathDefinitionSignatures() :
                new ArrayList<ResMathDefinitionSignature>();
    }

    @NotNull
    public List<ResUsesSpec> getUsesSpecs() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getUsesSpecs() :
                new ArrayList<ResUsesSpec>();
    }

    @NotNull
    public List<ResModuleSpec> getSuperModuleSpecList() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getSuperModuleSpecList() :
                new ArrayList<ResModuleSpec>();
    }

    @NotNull
    public List<ResTypeParamDecl> getGenericTypeParams() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        List<ResTypeParamDecl> genericTypes = new ArrayList<ResTypeParamDecl>();
        if (enclosedModule == null) return genericTypes;
        ResModuleParameters params = enclosedModule.getModuleParameters();
        if (params instanceof ResSpecModuleParameters) {
            genericTypes.addAll(((ResSpecModuleParameters) params)
                    .getTypeParamDeclList());
        }
        return genericTypes;
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
        List<ResOperationLikeNode> result = enclosedModule != null ?
                enclosedModule.getOperationLikeThings() :
                new ArrayList<ResOperationLikeNode>();
        return result;
    }

    /*@NotNull public List<ResAnnotatableOperationLikeNode> getOperationImpls() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getOperationsWithImpls() :
                new ArrayList<ResAnnotatableOperationLikeNode>();
    }*/
}
