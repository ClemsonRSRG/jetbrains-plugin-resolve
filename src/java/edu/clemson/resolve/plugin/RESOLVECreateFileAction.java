package edu.clemson.resolve.plugin;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;
import java.util.Set;

//Todo: not currently activated. Need to figure out how to use templates better with this approach
//to file creation (which I would personally prefer long term over the current mechansism)
public class RESOLVECreateFileAction extends CreateFileFromTemplateAction implements DumbAware {

    private static final String NEW_RESOLVE_FILE = "New RESOLVE File";
    private static final String FILE = "FILE";

    public RESOLVECreateFileAction() {
        super(NEW_RESOLVE_FILE, "", RESOLVEIcons.FILE);
    }

    @Override protected PsiFile createFile(String name,
                   @NotNull String templateName, @NotNull PsiDirectory dir) {
        FileTemplate template = FileTemplateManager
                .getInstance(dir.getProject())
                .getInternalTemplate(templateName);
        Properties properties = new Properties();

        try {
            PsiElement element = FileTemplateUtil.createFromTemplate(template,
                    name, properties, dir);
            if (element instanceof PsiFile) return (PsiFile)element;
        }
        catch (Exception e) {
            LOG.warn(e);
            return null;
        }
        return super.createFile(name, templateName, dir);
    }

    @Override protected void buildDialog(Project project,
                        PsiDirectory directory,
                        CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(NEW_RESOLVE_FILE)
                .addKind("Mathematical concept", RESOLVEIcons.CONCEPT, "Concept ResAbstractModule")
                .addKind("Mathematical precis", RESOLVEIcons.PRECIS, "Precis ResAbstractModule")
                .addKind("Concept implementation", RESOLVEIcons.IMPL, "Concept Implementation ResAbstractModule");
    }

    @Override protected String getActionName(PsiDirectory directory,
                                     String newName, String templateName) {
        return NEW_RESOLVE_FILE;
    }
}
