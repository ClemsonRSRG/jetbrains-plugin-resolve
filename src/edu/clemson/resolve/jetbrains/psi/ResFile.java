package edu.clemson.resolve.jetbrains.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.ResolveFileType;
import edu.clemson.resolve.jetbrains.ResolveLanguage;
import org.jetbrains.annotations.NotNull;

public class ResFile extends PsiFileBase {

    public ResFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ResolveLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ResolveFileType.INSTANCE;
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return super.getChildren();
    }
}
