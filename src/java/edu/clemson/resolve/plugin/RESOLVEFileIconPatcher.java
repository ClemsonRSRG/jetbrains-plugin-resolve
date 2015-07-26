package edu.clemson.resolve.plugin;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.ide.FileIconPatcher;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.*;

import javax.swing.*;

/**
 * Dynamically updates (patches) icons for {@link PsiFile} instances based on
 * module declared within.
 */
public class RESOLVEFileIconPatcher implements FileIconPatcher {

    @Override public Icon patchIcon(Icon baseIcon, VirtualFile file,
                                int flags, Project project) {
        if (project == null) {
            return baseIcon;
        }
        return replaceIcon(file, flags, project, baseIcon);
    }

    @SuppressWarnings("unchecked") private static Icon replaceIcon(
            VirtualFile file, int flags, Project project, Icon baseIcon) {
        final PsiFile f = PsiManager.getInstance(project).findFile(file);

        if (PsiTreeUtil.findChildOfAnyType(f, PrecisModule.class) != null) {
            return RESOLVEIcons.PRECIS;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                ConceptModule.class) != null) {
            return RESOLVEIcons.CONCEPT;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                ConceptImplModule.class) != null) {
            return RESOLVEIcons.IMPL;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                FacilityModule.class) != null) {
            return RESOLVEIcons.FACILITY;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                EnhancementModule.class) != null) {
            return RESOLVEIcons.ENHANCEMENT;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                EnhancementImplModule.class) != null) {
            return RESOLVEIcons.IMPL;
        }
        return baseIcon;
    }
}
