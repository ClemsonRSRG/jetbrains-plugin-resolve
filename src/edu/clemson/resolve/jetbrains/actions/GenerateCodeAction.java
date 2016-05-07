package edu.clemson.resolve.jetbrains.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.misc.Utils;
import org.jetbrains.jps.model.library.JpsLibrary;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static edu.clemson.resolve.jetbrains.actions.RunRESOLVEOnLanguageFile.OUTPUT_MODULE_NAME;

/**
 * Generate code from a RESOLVE language file; adapted for use in our plugin from the antlr4 grammar plugin by
 * Terence Parr.
 */
public class GenerateCodeAction extends RESOLVEAction {

    private static final Logger LOG = Logger.getInstance("RESOLVEGenerateCodeAction");

    @Override
    public void actionPerformed(final AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOG.error("actionPerformed (genCode): no project for " + e);
            return; // whoa!
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOG.info("actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE Code Generation";
        boolean canBeCancelled = true;

        // commit changes to PSI and file system
        commitDoc(project, resolveFile);

        boolean forceGeneration = true; // from action, they really mean it
        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        title,
                        canBeCancelled,
                        forceGeneration);
        gen.outputDir = gen.outputDir
                + getFilePathWithoutBase(project, resolveFile.getParent());

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());
        argMap.put("-package", buildPackage(project, resolveFile));
        argMap.put("-o", getOutputDir(project, resolveFile));
        argMap.put("-genCode", "Java");
        gen.addArgs(argMap);
        ProgressManager.getInstance().run(gen); //, "Generating", canBeCancelled, e.getData(PlatformDataKeys.PROJECT));

        // refresh from disk to see new files
        Set<File> generatedFiles = new HashSet<>();
        generatedFiles.add(new File(resolveFile.getPath()));
        LocalFileSystem.getInstance().refreshIoFiles(generatedFiles, true, true, null);
        // pop up a notification
        Notification notification =
                new Notification(RunRESOLVEOnLanguageFile.groupDisplayId,
                        "Java code for " + resolveFile.getName() + " generated",
                        "to " + argMap.get("-o"),
                        NotificationType.INFORMATION);
        Notifications.Bus.notify(notification, project);
    }

    //Bunch of methods for piecing together paths to gen, etc

    public String getOutputDir(Project project, VirtualFile vfile) {
        String result = RunRESOLVEOnLanguageFile.getOutputDir(project, vfile) +
                getFilePathWithoutBase(project, vfile);
        return result.substring(0, result.indexOf('.')) + File.separator;
    }

    private String getFilePathWithoutBase(Project project, VirtualFile vfile) {
        String basePath = RunRESOLVEOnLanguageFile.getContentRoot(project, vfile).getPath();
        String exactPath = vfile.getPath();
        return exactPath.substring(basePath.length());
    }

    private String buildPackage(Project project, VirtualFile vfile) {
        String result = getFilePathWithoutBase(project, vfile);
        result = result.substring(0, result.indexOf('.'));//strip extension
        if (result.startsWith(File.separator)) {
            result = result.substring(1);
        }
        return result.replace(File.separatorChar, '.');     //rpl all '/' with '.'
    }
}
