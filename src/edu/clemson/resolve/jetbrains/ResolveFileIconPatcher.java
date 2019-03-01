package edu.clemson.resolve.jetbrains;

import com.intellij.ide.FileIconPatcher;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import edu.clemson.resolve.jetbrains.psi.ResFile;

import javax.swing.*;

public class ResolveFileIconPatcher implements FileIconPatcher {

    @Override
    public Icon patchIcon(Icon baseIcon, VirtualFile file, int flags, Project project) {
        if (project == null) {
            return baseIcon;
        }
        return replaceIcon(file, flags, project, baseIcon);
    }

    private static Icon replaceIcon(VirtualFile file, int flags, Project project, Icon baseIcon) {
        final PsiFile f = PsiManager.getInstance(project).findFile(file);
        if (!(f instanceof ResFile)) return baseIcon;
        return ResolveIcons.RESOLVE_FILE;
    }
}