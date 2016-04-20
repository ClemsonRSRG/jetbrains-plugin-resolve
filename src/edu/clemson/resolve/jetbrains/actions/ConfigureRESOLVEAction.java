package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;

import edu.clemson.resolve.RESOLVECompiler;
import org.antlr.v4.Tool;

public class ConfigureRESOLVEAction extends AnAction implements DumbAware {
    public static final Logger LOG = Logger.getInstance("ConfigureRESOLVEAction");

    @Override public void update(AnActionEvent e) {
        GenerateCodeAction.selectedFileIsRESOLVEFile(e);
    }

    @Override public void actionPerformed(AnActionEvent e) {
        if ( e.getProject()==null ) {
            LOG.error("actionPerformed no project for "+e);
            return; // whoa!
        }
        VirtualFile resolveFile = GenerateCodeAction.getRESOLVEFileFromEvent(e);
        if ( resolveFile==null ) return;
        LOG.info("actionPerformed "+resolveFile);

        ConfigRESOLVEPerLanguageFile configDialog =
                new ConfigRESOLVEPerLanguageFile(e.getProject(),
                        resolveFile.getPath());
        configDialog.getPeer().setTitle("Configure RESOLVE Compiler "+
                RESOLVECompiler.VERSION+" for "+ resolveFile.getName());

        configDialog.show();
        if ( configDialog.getExitCode()== DialogWrapper.OK_EXIT_CODE ) {
            configDialog.saveValues(e.getProject(), resolveFile.getPath());
        }
    }
}
