package edu.clemson.resolve.jetbrains.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RESOLVECreateFileAction extends CreateFileFromTemplateAction implements DumbAware {

    public static final String FILE_TEMPLATE = "RESOLVE File";

    private static final String NEW_RESOLVE_FILE = "New RESOLVE File";

    public RESOLVECreateFileAction() {
        super(NEW_RESOLVE_FILE, "", RESOLVEIcons.MODULE);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, @NotNull CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(NEW_RESOLVE_FILE).addKind("Empty file", RESOLVEIcons.FILE, FILE_TEMPLATE);
    }

    @NotNull
    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return NEW_RESOLVE_FILE;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RESOLVECreateFileAction;
    }
}