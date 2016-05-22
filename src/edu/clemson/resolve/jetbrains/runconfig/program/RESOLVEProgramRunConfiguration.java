package edu.clemson.resolve.jetbrains.runconfig.program;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.util.PathUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.runconfig.RESOLVEModuleBasedConfiguration;
import edu.clemson.resolve.jetbrains.runconfig.ui.RESOLVERunConfigurationEditorForm;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class RESOLVEProgramRunConfiguration extends ModuleBasedConfiguration<RESOLVEModuleBasedConfiguration>
        implements
        RunConfigurationWithSuppressedDefaultRunAction,
        RunConfigurationWithSuppressedDefaultDebugAction {

    private static final String WORKING_DIRECTORY_NAME = "working_directory";
    private static final String RESOLVE_PARAMETERS_NAME = "resolve_parameters";
    private static final String PARAMETERS_NAME = "parameters";
    private static final String PASS_PARENT_ENV = "pass_parent_env";

    private String filePath = "";
    private String workingDirectory = "";

    public RESOLVEProgramRunConfiguration(String name, Project project, @NotNull ConfigurationType configurationType) {
        super(name, new RESOLVEModuleBasedConfiguration(project), configurationType.getConfigurationFactories()[0]);
    }

    protected RESOLVEProgramRunConfiguration(final String name,
                                             @NotNull RESOLVEModuleBasedConfiguration configurationModule,
                                             @NotNull ConfigurationFactory factory) {
        super(name, configurationModule, factory);
        Module module = configurationModule.getModule();
        if (module == null) {
            Collection<Module> modules = getValidModules();
            if (modules.size() == 1) {
                module = ContainerUtil.getFirstItem(modules);
                getConfigurationModule().setModule(module);
            }
        }
        this.workingDirectory = module != null
                ? StringUtil.trimEnd(PathUtil.getParentPath(module.getModuleFilePath()), ".idea")
                : StringUtil.notNullize(configurationModule.getProject().getBasePath());
        this.filePath = getWorkingDirectory();
    }

    @NotNull
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @NotNull
    @Override
    public Collection<Module> getValidModules() {
        return RESOLVESdkUtil.getRESOLVEModules(getProject());
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RESOLVERunConfigurationEditorForm(getProject());
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        RESOLVEModuleBasedConfiguration configurationModule = getConfigurationModule();
        Module module = configurationModule.getModule();
        if (module != null) {
            if (RESOLVESdkService.getInstance(module.getProject()).getSdkHomePath(module) == null) {
                throw new RuntimeConfigurationWarning("RESOLVE SDK is not specified for module '" +
                        module.getName() + "'");
            }
        }
        else {
            String moduleName = configurationModule.getModuleName();
            if (moduleName != null) {
                throw new RuntimeConfigurationError(ExecutionBundle.message("module.doesn.t.exist.in.project.error.text", moduleName));
            }
            throw new RuntimeConfigurationError(ExecutionBundle.message("module.not.specified.error.text"));
        }
        if (workingDirectory.isEmpty()) {
            throw new RuntimeConfigurationError("Working directory is not specified");
        }
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        RESOLVEModuleBasedConfiguration configuration = getConfigurationModule();
        Module module = configuration.getModule();
        if (module == null) {
            throw new ExecutionException("RESOLVE isn't configured for program run configuration: " + getName());
        }
        return newProgramRunningState(environment, module);
    }

    @NotNull
    protected RESOLVEProgramRunningState newProgramRunningState(@NotNull ExecutionEnvironment env,
                                                                @NotNull Module module) {
        return new RESOLVEProgramRunningState(env, module, this);
    }

    @NotNull
    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    @NotNull
    public String getWorkingDirectoryUrl() {
        return VfsUtilCore.pathToUrl(workingDirectory);
    }

    public void setWorkingDirectory(@NotNull String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
