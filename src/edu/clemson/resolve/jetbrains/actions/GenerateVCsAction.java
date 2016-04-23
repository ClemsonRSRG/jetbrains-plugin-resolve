package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

public class GenerateVCsAction extends AnAction implements DumbAware {

    @Override
    public void update(AnActionEvent e) {
        GenerateCodeAction.selectedFileIsRESOLVEFile(e);

    }

    @Override
    public void actionPerformed(AnActionEvent e) {

    }
}
