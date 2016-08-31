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
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.EnvironmentUtil;
import com.intellij.util.containers.*;
import com.intellij.util.containers.HashMap;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.jetbrains.RESOLVEPluginController;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.misc.Utils;
import edu.clemson.resolve.proving.Metrics;
import edu.clemson.resolve.proving.PerVCProverModel;
import edu.clemson.resolve.proving.ProverListener;
import edu.clemson.resolve.vcgen.model.VCOutputFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.misc.Misc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class RunRESOLVEOnLanguageFile extends Task.WithResult<Boolean, Exception> {

    public static final Logger LOG = Logger.getInstance("RunRESOLVEOnLanguageFile");

    //TODO: Generalize this.
    public static final String OUTPUT_MODULE_NAME = "gen";
    public static final String groupDisplayId = "RESOLVE Code Generation";

    public String targetFilePath;
    public Project project;
    public String outputDir;
    public boolean forceGeneration;
    private final List<String> args = new ArrayList<>();
    private VCOutputFile vcOutput = null;

    public RunRESOLVEOnLanguageFile(VirtualFile targetFile,
                                    @Nullable final Project project,
                                    @NotNull final String title) {
        this(targetFile.getPath(), project, title);
    }

    public RunRESOLVEOnLanguageFile(String targetFile,
                                    @Nullable final Project project,
                                    @NotNull final String title) {
        this(targetFile, project, title, false, true);
    }

    public RunRESOLVEOnLanguageFile(String targetFilePath,
                                    @Nullable final Project project,
                                    @NotNull final String title,
                                    final boolean canBeCancelled,
                                    boolean forceGeneration) {
        super(project, title, canBeCancelled);
        this.targetFilePath = targetFilePath;
        this.project = project;
        this.forceGeneration = forceGeneration;
        this.args.add(targetFilePath);
    }

    public VCOutputFile getVCOutput() {
        return vcOutput;
    }

    @Override
    protected Boolean compute(@NotNull ProgressIndicator indicator) throws Exception {
        return resolve(targetFilePath);
    }

    //See "ImplementMethodsFix" in the intellij sources

    /**
     * Run RESOLVE on file according to preferences in intellij for this file.
     * Writes set of generated files or empty set if error.
     */
    public Boolean resolve(String targetFilePath) {
        if (targetFilePath == null) return false;
        LOG.info("resolve(\"" + targetFilePath + "\")");

        // String sourcePath = ConfigRESOLVEPerLanguageFile.getParentDir(vfile);

        LOG.info("args: " + Utils.join(args, " "));
        RESOLVECompiler compiler = new RESOLVECompiler(args.toArray(new String[args.size()]));
        ConsoleView console = RESOLVEPluginController.getInstance(project).getConsole();
        RESOLVEPluginController.getInstance(project).console.clear(); //clear it for the current run

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        console.print(timeStamp + ": resolve " + Utils.join(args, " ") + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);

        compiler.removeListeners();
        RunRESOLVEListener listener = new RunRESOLVEListener(compiler, console);
        compiler.addListener(listener);

        try {
            compiler.processCommandLineTargets();
        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String msg = sw.toString();
            Notification notification =
                    new Notification(groupDisplayId, "failed to execute " + targetFilePath,
                            e.toString(),
                            NotificationType.INFORMATION);
            Notifications.Bus.notify(notification, project);
            console.print(timeStamp + ": resolve " + msg + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
            listener.hasOutput = true; // show console below
        }
        //Todo: Eventually make it a list (or map) and allow vcs to come back for any target file (in case this class
        //is ever used to compile multiple resolve files at once (not sure when, but you never know).
        if (compiler.commandlineTargets.size() == 1) {
            vcOutput = compiler.commandlineTargets.get(0).getVCOutput();
        }
        if (listener.hasOutput) {
            RESOLVEPluginController.showConsoleWindow(project);
        }
        return compiler.errMgr.getErrorCount() == 0;
    }

    public void addArgs(Map<String, String> argMap) {
        this.args.addAll(getRESOLVEArgsAsList(argMap));
    }

    public static List<String> getRESOLVEArgsAsList(Map<String, String> argMap) {
        List<String> args = new ArrayList<>();
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
        return contentRoot.getPath() + File.separator + OUTPUT_MODULE_NAME + File.separator;
    }

    public static VirtualFile getContentRoot(Project project, VirtualFile vfile) {
        VirtualFile root = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(vfile);
        if (root != null) return root;
        return vfile.getParent();
    }

    public static VirtualFile getContentGenRoot(Project project, VirtualFile vfile) {
        VirtualFile root = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(vfile);
        if (root != null) return root;
        return vfile.getParent();
    }
}
