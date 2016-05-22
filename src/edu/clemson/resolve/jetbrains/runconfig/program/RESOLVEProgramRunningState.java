package edu.clemson.resolve.jetbrains.runconfig.program;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class RESOLVEProgramRunningState extends CommandLineState {

    @NotNull protected final RESOLVEProgramRunConfiguration configuration;

    public RESOLVEProgramRunningState(@NotNull ExecutionEnvironment env,
                                      @NotNull Module module,
                                      @NotNull RESOLVEProgramRunConfiguration configuration) {
        super(env);
        this.module = module;
        this.configuration = configuration;
        //TODO
        //addConsoleFilters(new RESOLVEConsoleFilter(
        //        configuration.getProject(), module, configuration.getWorkingDirectoryUrl()));
    }

    @NotNull
    protected final Module module;

    @NotNull
    public RESOLVEProgramRunConfiguration getConfiguration() {
        return configuration;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        System.out.println("StartProcess");

        final Project project = runConfiguration.getProject();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);

        final VirtualFile[] sourceRoots = projectRootManager.getContentSourceRoots();
        final String sourcePath = getSourcePath(sourceRoots);
        final String fregeClassPath = getClassPathSource();
        if (fregeClassPath == null) {
            return echo("Could not find frege in classpath");
        }
        final String projectOutPath = project.getBasePath() + "/out";
        final String effectiveClassPath = fregeClassPath + ":" + projectOutPath;

    /*
     * Cross compile from frege to java
     */
        ProcessHandler fregec = compile(sourcePath, ".fr", "Could not compile with fregec",
                file -> fregeCompile(sourcePath, projectOutPath, file));
        if (fregec != null) return fregec;

    /*
     * Compile Java to bytecode
     */
        ProcessHandler javac = compile(projectOutPath, ".java", "Could not compile with javac", file ->
                com.sun.tools.javac.Main.compile(new String[]{"-cp", effectiveClassPath, "-d", projectOutPath, file}) == 0
        );
        if (javac != null) return javac;
    /*
     * Execute bytecode
     */
        final KillableColoredProcessHandler processHandler = new KillableColoredProcessHandler(new GeneralCommandLine(
                "java",
                "-cp", effectiveClassPath,
                runConfiguration.getClassName()
        ));
        processHandler.setShouldDestroyProcessRecursively(true);
        return processHandler;
    }
}
