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
import com.intellij.openapi.util.io.FileUtil;
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
import java.util.*;

public class RESOLVEProgramRunningState extends CommandLineState {

    @NotNull
    protected final RESOLVEProgramRunConfiguration configuration;
    @NotNull
    protected final Module module;

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
    public RESOLVEProgramRunConfiguration getConfiguration() {
        return configuration;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        System.out.println("StartProcess");

        final Project project = configuration.getProject();
        String filePath = configuration.getFilePath();
        String outputPath = project.getBasePath() + File.separator + "out";
        String classPath = RESOLVESdkService.getInstance(project).getSdkCompilerJarPath(module) + ":" + outputPath;
        String className = getClassName(project, filePath);

        //Cross compile from RESOLVE to java
        generateAndWriteJava(project, filePath, outputPath);

        //Compile Java to bytecode and store in /out/ directory
        ProcessHandler javac = compileGeneratedJava(classPath, outputPath, filePath);
        if (javac != null) return javac;

        //Execute bytecode
        final KillableColoredProcessHandler processHandler = new KillableColoredProcessHandler(new GeneralCommandLine(
                "java",
                "-cp", classPath,
                className
        ));
        processHandler.setShouldDestroyProcessRecursively(true);
        return processHandler;
    }

    @NotNull public String getOutputPathForFile(@NotNull Project project,
                                             @NotNull String baseOutputPath,
                                             @NotNull String filePath) {
        String path = getRelativePath(project, filePath);
        return baseOutputPath + File.separator + path;
    }

    @NotNull public String getClassName(@NotNull Project project, @NotNull String filePath) {
        String result = getRelativePath(project, filePath);
        result = result.substring(0, result.lastIndexOf('.'));
        result = project.getName() + File.separator + result;

        return result.replaceAll(File.separator, ".");
    }

    private String getRelativePath(@NotNull Project project, @NotNull String filePath) {
        String basePath = filePath;
        //can't think of why this would be null, but intellij is telling me it could be..
        if (project.getBasePath() != null) basePath = project.getBasePath();
        return FileUtil.getRelativePath(basePath, filePath, File.separatorChar);
    }

    public void generateAndWriteJava(Project project, String filePath, String outputPath) {
        RunRESOLVEOnLanguageFile g = new RunRESOLVEOnLanguageFile(configuration.getFilePath(), project, "gencode");
        g.outputDir = outputPath;
        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", project.getBasePath());
        argMap.put("-o", outputPath);
        argMap.put("-genCode", "Java");
        g.addArgs(argMap);
        ProgressManager.getInstance().run(g);
    }

    public ProcessHandler compileGeneratedJava(Project project, String effectiveClassPath, String outputPath, String filePath)
            throws ExecutionException {
        List<String> fileNames = new ArrayList<>();
        File outFile = new File(outputPath);
        String newOutputPath = getOutputPathForFile(project, outputPath, filePath);

        //TODO: I think the compiler does this automatically in generateAndWriteJava(..). check this.
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        final File[] fileList = outFile.listFiles();
        if (fileList == null || fileList.length == 0) {
            return echo("Filelist could not be compiled");
        }

        for (File file : fileList) {
            if (file.getAbsolutePath().endsWith(".java"))
                fileNames.add(file.getAbsolutePath());
        }
        for (String file : fileNames) {
            boolean status = com.sun.tools.javac.Main.compile(
                    new String[]{"-cp", effectiveClassPath, "-d", inFileDir.getPath(), file}) == 0;
            if (!status) {
                return echo("compilation failed");
            }
        }
        return null;
    }

    @NotNull
    private KillableColoredProcessHandler echo(String message) throws ExecutionException {
        return new KillableColoredProcessHandler(new GeneralCommandLine(
                "echo",
                message
        ));
    }

}
