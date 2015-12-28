package edu.clemson.resolve.jetbrains.actions;

import com.google.common.base.Strings;
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
import edu.clemson.resolve.compiler.RESOLVECompiler;
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
    public static final String OUTPUT_DIR_NAME = "gen" ;
    public static final String MISSING = "";
    public static final String groupDisplayId = "RESOLVE Code Generation";

    public VirtualFile targetFile;
    public Project project;
    public boolean forceGeneration;

    public RunRESOLVEOnLanguageFile(VirtualFile targetFile,
                                    @Nullable final Project project,
                                    @NotNull final String title,
                                    final boolean canBeCancelled,
                                    boolean forceGeneration) {
        super(project, title, canBeCancelled);
        this.targetFile = targetFile;
        this.project = project;
        this.forceGeneration = forceGeneration;
    }

    @Override public void run(@NotNull ProgressIndicator indicator) {
        indicator.setIndeterminate(true);
        if (forceGeneration) {
            resolve(targetFile);
        }
    }

    /** Run RESOLVE on file according to preferences in intellij for this file.
     *  Writes set of generated files or empty set if error.
     */
    public void resolve(VirtualFile vfile) {
        if ( vfile==null ) return;
        LOG.info("resolve(\""+vfile.getPath()+"\")");
        List<String> args = getRESOLVEArgsAsList(project, vfile);

        String sourcePath = ConfigRESOLVEPerLanguageFile.getParentDir(vfile);
        String fullyQualifiedInputFileName =
                sourcePath+ File.separator+vfile.getName();
        args.add(fullyQualifiedInputFileName); // add grammar file last

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

    public static List<String> getRESOLVEArgsAsList(Project project,
                                                    VirtualFile vfile) {
        Map<String,String> argMap = getRESOLVEArgs(project, vfile);
        List<String> args = new ArrayList<String>();
        for (String option : argMap.keySet()) {
            args.add(option);
            String value = argMap.get(option);
            if ( value.length()!=0 ) {
                args.add(value);
            }
        }
        return args;
    }

    public static Map<String, String> getRESOLVEArgs(Project project,
                                                     VirtualFile vfile) {
        Map<String,String> args = new HashMap<String, String>();
        String qualFileName = vfile.getPath();
        String package_ = ConfigRESOLVEPerLanguageFile.getProp(
                project, qualFileName, ConfigRESOLVEPerLanguageFile
                        .PROP_PACKAGE, MISSING);
        if ( package_.equals(MISSING) ) {
            package_ = ProjectRootManager.getInstance(project).getFileIndex()
                    .getPackageNameByDirectory(vfile.getParent());
            if ( Strings.isNullOrEmpty(package_)) {
                package_ = MISSING;
            }
        }
       if ( !package_.equals(MISSING)) {
           args.put("-package", package_);
       }

        VirtualFile contentRoot =
                ConfigRESOLVEPerLanguageFile.getContentRoot(project, vfile);
        String outputDirName =
                ConfigRESOLVEPerLanguageFile.getOutputDirName(
                        project, qualFileName, contentRoot, package_);
        args.put("-o", outputDirName);
        args.put("-genCode", "Java");

        String libDir = contentRoot.getPath();
        args.put("-lib", libDir);

        return args;
    }

    public String getOutputDirName() {
        VirtualFile contentRoot =
                ConfigRESOLVEPerLanguageFile.getContentRoot(project, targetFile);
        Map<String,String> argMap = getRESOLVEArgs(project, targetFile);
        String package_ = argMap.get("-package");
        if ( package_==null ) {
            package_ = MISSING;
        }
        return ConfigRESOLVEPerLanguageFile.getOutputDirName(project,
                targetFile.getPath(), contentRoot, package_);
    }

}
