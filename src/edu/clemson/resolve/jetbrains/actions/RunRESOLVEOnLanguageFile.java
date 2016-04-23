package edu.clemson.resolve.jetbrains.actions;

import com.google.common.base.Strings;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.jetbrains.RESOLVEPluginController;
import edu.clemson.resolve.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.misc.Misc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class RunRESOLVEOnLanguageFile extends Task.Modal {

    public static final Logger LOG = Logger.getInstance("RunRESOLVEOnLanguageFile");
    public static final String OUTPUT_MODULE_NAME = "gen";
    public static final String OUTPUT_DIR_NAME = "src";
    public static final String groupDisplayId = "RESOLVE Code Generation";

    public VirtualFile targetFile;
    public Project project;
    public String outputDir;
    public boolean forceGeneration;
    private final List<String> args = new ArrayList<>();

    public RunRESOLVEOnLanguageFile(VirtualFile targetFile,
                                    @Nullable final Project project,
                                    @NotNull final String title,
                                    final boolean canBeCancelled,
                                    boolean forceGeneration) {
        super(project, title, canBeCancelled);
        this.targetFile = targetFile;
        this.project = project;
        this.forceGeneration = forceGeneration;
        String fullyQualifiedInputFileName = targetFile.getCanonicalPath();
        this.args.add(fullyQualifiedInputFileName);
    }

    @Override public void run(@NotNull ProgressIndicator indicator) {
        indicator.setIndeterminate(true);
        if (forceGeneration) {
            resolve(targetFile);
        }
    }

    //See "ImplementMethodsFix" in the intellij sources
    /** Run RESOLVE on file according to preferences in intellij for this file.
     *  Writes set of generated files or empty set if error.
     */
    public void resolve(VirtualFile vfile) {
        if ( vfile==null ) return;
        LOG.info("resolve(\""+vfile.getPath()+"\")");

       // String sourcePath = ConfigRESOLVEPerLanguageFile.getParentDir(vfile);

        LOG.info("args: " + Utils.join(args, " "));
        RESOLVECompiler compiler =
                new RESOLVECompiler(args.toArray(new String[args.size()]));
        ConsoleView console =
                RESOLVEPluginController.getInstance(project).getConsole();
        String timeStamp =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(Calendar.getInstance()
                                .getTime());

        console.print(timeStamp+": resolve "+ Utils.join(args, " ")+"\n",
                ConsoleViewContentType.SYSTEM_OUTPUT);

        compiler.removeListeners();
        RunRESOLVEListener listener = new RunRESOLVEListener(compiler, console);
        compiler.addListener(listener);

        try {
            compiler.processCommandLineTargets();
        }
        catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String msg = sw.toString();
            Notification notification =
                    new Notification(groupDisplayId,
                            "can't generate code for " + vfile.getName(),
                            e.toString(),
                            NotificationType.INFORMATION);
            Notifications.Bus.notify(notification, project);
            console.print(timeStamp + ": resolve " + msg + "\n",
                    ConsoleViewContentType.SYSTEM_OUTPUT);
            listener.hasOutput = true; // show console below
        }

        if ( listener.hasOutput ) {
            RESOLVEPluginController.showConsoleWindow(project);
        }
    }

    public void addArgs(Map<String, String> argMap) {
        this.args.addAll(getRESOLVEArgsAsList(argMap));
    }

    public static List<String> getRESOLVEArgsAsList(Map<String, String> argMap) {
        List<String> args = new ArrayList<String>();
        for (String option : argMap.keySet()) {
            args.add(option);
            String value = argMap.get(option);
            if (value.length() != 0) {
                args.add(value);
            }
        }
        return args;
    }

    public static String getOutputDir(Project project, VirtualFile vfile) {
        VirtualFile contentRoot = getContentRoot(project, vfile);
        return contentRoot.getPath()+File.separator+
                OUTPUT_MODULE_NAME+File.separator+
                RunRESOLVEOnLanguageFile.OUTPUT_DIR_NAME;
    }

    public static VirtualFile getContentRoot(Project project, VirtualFile vfile) {
        VirtualFile root =
                ProjectRootManager.getInstance(project)
                        .getFileIndex().getContentRootForFile(vfile);
        if (root != null) return root;
        return vfile.getParent();
    }
}
