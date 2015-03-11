package edu.clemson.resolve.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import edu.clemson.resolve.plugin.PrecisFileType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVEPrecisFile extends PsiFileBase {
    public RESOLVEPrecisFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RESOLVELanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PrecisFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "a precis file";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
