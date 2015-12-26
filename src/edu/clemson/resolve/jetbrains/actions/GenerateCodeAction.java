package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;

/** Generate code from a RESOLVE language file;
 *  adapted for use in our plugin from the antlr4 grammar plugin by
 *  Terence Parr.
 */
public class GenerateCodeAction extends AnAction implements DumbAware {

    public static final Logger LOG = Logger.getInstance("RESOLVE GenerateAction");

    @Override public void update(AnActionEvent e) {
        selectedFileIsRESOLVEFile(e);
    }

    public static void selectedFileIsRESOLVEFile(AnActionEvent e) {
        VirtualFile vfile = getRESOLVEFileFromEvent(e);
        if ( vfile==null ) {
            e.getPresentation().setEnabled(false);
            return;
        }
        e.getPresentation().setEnabled(true); // enable action if we're looking at RESOLVE file
        e.getPresentation().setVisible(true);
    }

    public static VirtualFile getRESOLVEFileFromEvent(AnActionEvent e) {
        VirtualFile[] files =
                LangDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if ( files==null || files.length==0 ) return null;
        VirtualFile vfile = files[0];
        if ( vfile!=null && vfile.getName().endsWith(".resolve") ) {
            return vfile;
        }
        return null;
    }

    @Override public void actionPerformed(final AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if ( project==null ) {
            LOG.error("actionPerformed no project for "+e);
            return; // whoa!
        }
        VirtualFile grammarFile = getRESOLVEFileFromEvent(e);
        LOG.info("actionPerformed "+(grammarFile==null ? "NONE" : grammarFile));
        if ( grammarFile==null ) return;
        String title = "RESOLVE Code Generation";
        boolean canBeCancelled = true;

        // commit changes to PSI and file system
        PsiDocumentManager psiMgr = PsiDocumentManager.getInstance(project);
        FileDocumentManager docMgr = FileDocumentManager.getInstance();
        Document doc = docMgr.getDocument(grammarFile);
        if ( doc==null ) return;

        boolean unsaved = !psiMgr.isCommitted(doc) || docMgr.isDocumentUnsaved(doc);
        if ( unsaved ) {
            psiMgr.commitDocument(doc);
            docMgr.saveDocument(doc);
        }

        boolean forceGeneration = true; // from action, they really mean it
    }
}
