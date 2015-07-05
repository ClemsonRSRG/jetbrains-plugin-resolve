package edu.clemson.resolve.plugin;

import com.intellij.ide.FileIconPatcher;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import edu.clemson.resolve.plugin.psi.impl.RConceptImplModule;
import edu.clemson.resolve.plugin.psi.impl.RConceptModule;
import edu.clemson.resolve.plugin.psi.impl.RFacilityModule;
import edu.clemson.resolve.plugin.psi.impl.RPrecisModule;
import org.antlr.intellij.adaptor.lexer.TokenElementType;

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

        //Todo: this might be prohibitively expensive (as findChildOfAnyType
        //is doing a depth first search). Though in the future hopefully our
        //psi structure will be better defined so we can just do the following:
        // PsiTreeUtil.getChildOfType(f, ParserClass.class);
        if (PsiTreeUtil.findChildOfAnyType(f, RPrecisModule.class) != null) {
            return RESOLVEIcons.PRECIS;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                RConceptModule.class) != null) {
            return RESOLVEIcons.CONCEPT;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                RConceptImplModule.class) != null) {
            return RESOLVEIcons.IMPL;
        }
        else if (PsiTreeUtil.findChildOfAnyType(f,
                RFacilityModule.class) != null) {
            return RESOLVEIcons.FACILITY;
        }
        return baseIcon;
    }
}
