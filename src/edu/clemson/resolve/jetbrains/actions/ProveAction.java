package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

public class ProveAction extends RESOLVEAction {

    private static final Logger LOGGER = Logger.getInstance("RESOLVEProveAction");

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation();
        super.update(e); //checks we're dealing with a resolve file (and that's it)
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOGGER.error("actionPerformed (genVCs): no project for " + e);
            return; // whoa!
        }
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;
        //e
        e.notify();
    }
}
