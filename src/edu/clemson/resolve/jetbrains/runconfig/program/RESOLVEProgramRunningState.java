package edu.clemson.resolve.jetbrains.runconfig.program;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import edu.clemson.resolve.jetbrains.actions.RunRESOLVEOnLanguageFile;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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

        final Project project = configuration.getProject();
        String filePath = configuration.getFilePath();
        VirtualFile resolveFile = project.getBaseDir().findChild(configuration.getFilePath());
        int i;
        i=0;
       /* RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        title,
                        canBeCancelled,
                        forceGeneration);
        gen.outputDir = gen.outputDir + getFilePathWithoutBase(project, resolveFile.getParent());

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());

        //argMap.put("-package", buildPackage(project, resolveFile));
        argMap.put("-o", RunRESOLVEOnLanguageFile.getOutputDir(project, resolveFile));
        argMap.put("-genfake", "");
        gen.addArgs(argMap);
        ProgressManager.getInstance().run(gen); //, "Generating",*/
        /*final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);

        final VirtualFile[] sourceRoots = projectRootManager.getContentSourceRoots();


        final String sourcePath = getSourcePath(sourceRoots);
        final String fregeClassPath = getClassPathSource();
        if (fregeClassPath == null) {
            return echo("Could not find frege in classpath");
        }*/
        String x = RESOLVESdkService.getInstance(project).getSdkHomePath(module);
 /*       final String projectOutPath = project.getBasePath() + "/out";
        final String effectiveClassPath = fregeClassPath + ":" + projectOutPath;

     //Cross compile from frege to java
        ProcessHandler fregec = compile(sourcePath, ".fr", "Could not compile with fregec",
                file -> fregeCompile(sourcePath, projectOutPath, file));
        if (fregec != null) return fregec;

     //Compile Java to bytecode

        ProcessHandler javac = compile(projectOutPath, ".java", "Could not compile with javac", file ->
                com.sun.tools.javac.Main.compile(new String[]{"-cp", effectiveClassPath, "-d", projectOutPath, file}) == 0
        );
        if (javac != null) return javac;
    //Execute bytecode
     */
        final KillableColoredProcessHandler processHandler = new KillableColoredProcessHandler(new GeneralCommandLine(
                "java",
                "-cp", "",
                configuration.getFilePath()
        ));
        processHandler.setShouldDestroyProcessRecursively(true);
        return processHandler;
    }
}
