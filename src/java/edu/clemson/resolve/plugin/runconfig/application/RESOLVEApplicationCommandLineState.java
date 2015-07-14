package edu.clemson.resolve.plugin.runconfig.application;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.jar.JarApplicationCommandLineState;
import com.intellij.execution.jar.JarApplicationConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ProjectBundle;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.util.SystemProperties;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class RESOLVEApplicationCommandLineState extends JavaCommandLineState {
    @NotNull private final RESOLVERunConfigurationBase runConfiguration;

    protected RESOLVEApplicationCommandLineState(
            @NotNull RESOLVERunConfigurationBase runConfiguration,
            ExecutionEnvironment environment) {
        super(environment);
        this.runConfiguration = runConfiguration;
    }
    private static Sdk ourDefaultSdk;

    //Todo: I'm probably doing something really dumb here.
    private static synchronized Sdk getDefaultSdk() {
        if (ourDefaultSdk == null) {
            final String jdkHome = SystemProperties.getJavaHome();
            final String versionName = ProjectBundle.message("sdk.java.name.template", SystemProperties.getJavaVersion());
            Sdk sdk = ProjectJdkTable.getInstance().createSdk(versionName, new SimpleJavaSdkType());
            SdkModificator modificator = sdk.getSdkModificator();
            modificator.setHomePath(jdkHome);
            modificator.commitChanges();
            ourDefaultSdk = sdk;
        }
        return ourDefaultSdk;
    }
    @Override protected JavaParameters createJavaParameters()
            throws ExecutionException {
        final JavaParameters parameters = new JavaParameters();

        ParametersList vm = parameters.getVMParametersList();
        Sdk jdk = getDefaultSdk();

        Module m = runConfiguration.getModules()[0];
        final ModuleRootManager rootManager = ModuleRootManager.getInstance(m);
        final Sdk projectSdk = rootManager.getSdk();

        @NonNls String libPath = projectSdk.getHomePath();

        //our favorite jar (the compiler) must be on the classpath for us to set the
        //Main class.. TODO : Don't hardcode jar name.
        vm.add("-Xbootclasspath/a:" + libPath + File.separator + "resolve-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        parameters.setJdk(jdk);
        parameters.setMainClass("edu.clemson.resolve.compiler.RESOLVECompiler");
        parameters.getProgramParametersList().add(runConfiguration.getFilePath());
        parameters.getProgramParametersList().addParametersString(" -lib " + runConfiguration.getWorkingDirectory());
        return parameters;
    }
}
