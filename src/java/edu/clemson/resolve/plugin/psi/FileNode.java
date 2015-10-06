package edu.clemson.resolve.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.psi.impl.FacilityModule;
import edu.clemson.resolve.plugin.psi.impl.OperationProcedureDeclImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class FileNode extends PsiFileBase {

    public FileNode(@NotNull FileViewProvider viewProvider) {
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

    public boolean holdsValidExecutableModule() {
        boolean result = PsiTreeUtil.findChildOfType(this,
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
        return result;
    }
}
