package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.jar.JarApplicationCommandLineState;
import com.intellij.execution.jar.JarApplicationConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ProjectBundle;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.util.SystemProperties;
import org.jetbrains.annotations.NotNull;

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
        parameters.configureByModule(runConfiguration.getModules()[0], JavaParameters.CLASSES_ONLY);
        parameters.setWorkingDirectory(runConfiguration.getWorkingDirectory());
        Sdk x = getDefaultSdk();
        parameters.setJdk(x);
        parameters.setMainClass("edu.clemson.resolve.compiler.RESOLVECompiler.java");
        int i;
        i = 0;
        return parameters;
    }
}
