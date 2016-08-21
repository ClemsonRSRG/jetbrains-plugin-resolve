package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;

abstract class RESOLVEAction extends AnAction implements DumbAware {

    @Override
    public void update(AnActionEvent e) {
        selectedFileIsRESOLVEFile(e);
    }

    private void selectedFileIsRESOLVEFile(AnActionEvent e) {
        VirtualFile vfile = getRESOLVEFileFromEvent(e);
        if (vfile == null || e.getProject() == null) {
            e.getPresentation().setEnabled(false);
            return;
        }
        Project project = e.getProject();
        Module enclosingModule = ModuleUtilCore.findModuleForFile(vfile, e.getProject());
        if (!RESOLVESdkService.getInstance(e.getProject()).isRESOLVEModule(enclosingModule)) {
            e.getPresentation().setEnabled(false);
            return;
        }
        // only enable action if we're looking at RESOLVE file in a RESOLVE module
        e.getPresentation().setEnabled(true);
        e.getPresentation().setVisible(true);

    }

    VirtualFile getRESOLVEFileFromEvent(AnActionEvent e) {
        VirtualFile[] files = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if (files == null || files.length == 0) return null;
        VirtualFile vfile = files[0];
        if (vfile != null && vfile.getName().endsWith("." + RESOLVEFileType.INSTANCE.getDefaultExtension())) {
            return vfile;
        }
        return null;
    }

    void commitDoc(Project project, VirtualFile file) {
        PsiDocumentManager psiMgr = PsiDocumentManager.getInstance(project);
        FileDocumentManager docMgr = FileDocumentManager.getInstance();
        Document doc = docMgr.getDocument(file);
        if (doc == null) return;

        boolean unsaved = !psiMgr.isCommitted(doc) || docMgr.isDocumentUnsaved(doc);
        if (unsaved) {
            psiMgr.commitDocument(doc);
            docMgr.saveDocument(doc);
        }
    }
}
