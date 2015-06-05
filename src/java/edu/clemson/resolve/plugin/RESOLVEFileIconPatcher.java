package edu.clemson.resolve.plugin;

import com.intellij.ide.FileIconPatcher;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.impl.RFacilityModuleImpl;
import edu.clemson.resolve.plugin.psi.impl.RPrecisModuleImpl;

import javax.swing.*;

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
        //return PsiTreeUtil.getChildOfType(file, ParserClass.class);

        //Todo: this might be prohibitively expensive (as findChildOfAnyType
        //is doing a depth first search). Though in the future hopefully our
        //psi structure will be better defined so we can just make the following
        //call: PsiTreeUtil.getChildOfType(f, ParserClass.class);
        if (PsiTreeUtil.findChildOfAnyType(f, RPrecisModuleImpl.class) != null) {
            return RESOLVEIcons.PRECIS;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                RFacilityModuleImpl.class) != null) {
            return RESOLVEIcons.FACILITY;
        }
        return baseIcon;
    }
}
