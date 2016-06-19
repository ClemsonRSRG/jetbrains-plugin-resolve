package edu.clemson.resolve.jetbrains.runconfig.program;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.PathUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.runconfig.RESOLVEModuleBasedConfiguration;
import edu.clemson.resolve.jetbrains.runconfig.RESOLVERunUtil;
import edu.clemson.resolve.jetbrains.runconfig.ui.RESOLVERunConfigurationEditorForm;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class RESOLVEProgramRunConfiguration extends ModuleBasedConfiguration<RESOLVEModuleBasedConfiguration>
        implements
        RunConfigurationWithSuppressedDefaultDebugAction {

    private static final String FILE_PATH_ATTRIBUTE_NAME = "filePath";
    private static final String WORKING_DIRECTORY_NAME = "working_directory";
    private static final String RESOLVE_PARAMETERS_NAME = "resolve_parameters";
    private static final String PARAMETERS_NAME = "parameters";
    private static final String PASS_PARENT_ENV = "pass_parent_env";

    private String filePath = "";
    private String workingDirectory = "";

    RESOLVEProgramRunConfiguration(String name, Project project, @NotNull ConfigurationType configurationType) {
        this(name, new RESOLVEModuleBasedConfiguration(project), configurationType.getConfigurationFactories()[0]);
    }

    private RESOLVEProgramRunConfiguration(final String name,
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

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        readModule(element);
        //resolveParams = StringUtil.notNullize(JDOMExternalizerUtil.getFirstChildValueAttribute(element, RESOLVE_PARAMETERS_NAME));
        //params = StringUtil.notNullize(JDOMExternalizerUtil.getFirstChildValueAttribute(element, PARAMETERS_NAME));

        String workingDirectoryValue = JDOMExternalizerUtil.getFirstChildValueAttribute(element, WORKING_DIRECTORY_NAME);
        if (workingDirectoryValue != null) {
            this.workingDirectory = workingDirectoryValue;
        }
        //EnvironmentVariablesComponent.readExternal(element, customEnvironment);

        String passEnvValue = JDOMExternalizerUtil.getFirstChildValueAttribute(element, PASS_PARENT_ENV);
        //myPassParentEnvironment = passEnvValue == null || Boolean.valueOf(passEnvValue);
        this.filePath = StringUtil.notNullize(
                JDOMExternalizerUtil.getFirstChildValueAttribute(element, FILE_PATH_ATTRIBUTE_NAME));
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        writeModule(element);
        if (StringUtil.isNotEmpty(workingDirectory)) {
            JDOMExternalizerUtil.addElementWithValueAttribute(element, WORKING_DIRECTORY_NAME, workingDirectory);
        }
        if (StringUtil.isNotEmpty(filePath)) {
            JDOMExternalizerUtil.addElementWithValueAttribute(element, FILE_PATH_ATTRIBUTE_NAME, filePath);
        }
    }

    protected void checkFileConfiguration() throws RuntimeConfigurationError {
        VirtualFile file = findFile(getFilePath());
        if (file == null) {
            throw new RuntimeConfigurationError("Main file is not specified");
        }
        PsiFile psiFile = PsiManager.getInstance(getProject()).findFile(file);
        if (!(psiFile instanceof ResFile)) {
            throw new RuntimeConfigurationError("Main file is invalid (not a .resolve file)");
        }
        if (!RESOLVERunUtil.isMainRESOLVEFile(psiFile)) {
            throw new RuntimeConfigurationError("Main file is not a facility and/or doesn't contain main function");
        }
    }

    @Nullable
    protected VirtualFile findFile(@NotNull String filePath) {
        VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(VfsUtilCore.pathToUrl(filePath));
        if (virtualFile == null) {
            String path = FileUtil.join(getWorkingDirectory(), filePath);
            virtualFile = VirtualFileManager.getInstance().findFileByUrl(VfsUtilCore.pathToUrl(path));
        }
        return virtualFile;
    }

    @NotNull
    @Override
    protected ModuleBasedConfiguration createInstance() {
        return new RESOLVEProgramRunConfiguration(getName(), getProject(),
                RESOLVEProgramRunConfigurationType.getInstance());
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
        return new RESOLVEProgramRunningState(environment, module, this);
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
