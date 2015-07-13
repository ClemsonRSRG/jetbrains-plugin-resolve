package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.configurations.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.PathUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkService;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class RESOLVERunConfigurationBase
        extends
            ModuleBasedConfiguration<RESOLVEModuleBasedConfiguration> {

    @NotNull private String resolveToolParams = "";
    @NotNull private String workingDirectory = "";
    @NotNull private String params = "";

    public RESOLVERunConfigurationBase(String name,
                   RESOLVEModuleBasedConfiguration configurationModule,
                   ConfigurationFactory factory) {
        super(name, configurationModule, factory);
        Module module = configurationModule.getModule();
        if (module == null) {
            Collection<Module> modules = getValidModules();
            if (modules.size() == 1) {
                module = ContainerUtil.getFirstItem(modules);
                getConfigurationModule().setModule(module);
            }
        }

        if (module != null) {
            this.workingDirectory = StringUtil.trimEnd(PathUtil
                    .getParentPath(module.getModuleFilePath()), ".idea");
        }
        else {
            this.workingDirectory = StringUtil.notNullize(
                    configurationModule.getProject().getBasePath());
        }
    }

    @Override public void checkConfiguration()
            throws RuntimeConfigurationException {
        final RESOLVEModuleBasedConfiguration configurationModule =
                getConfigurationModule();
        final Module module = configurationModule.getModule();
        if (module != null) {
            if (RESOLVESdkService.getInstance(module.getProject())
                    .getSdkHomePath(module) == null) {
                throw new RuntimeConfigurationWarning(
                        "RESOLVE SDK is not specified for module '" +
                                module.getName() + "'");
            }
        }
        else {
            final String moduleName = configurationModule.getModuleName();
            if (moduleName != null) {
                throw new RuntimeConfigurationError(ExecutionBundle.message(
                        "module.doesn.t.exist.in.project.error.text", moduleName));
            }
            throw new RuntimeConfigurationError(ExecutionBundle.message(
                    "module.not.specified.error.text"));
        }
        if (this.workingDirectory.isEmpty()) {
            throw new RuntimeConfigurationError(
                    "Working directory is not specified");
        }
    }

    @NotNull public String getWorkingDirectory() {
        return workingDirectory;
    }

    @NotNull public String getRESOLVEToolParams() {
        return resolveToolParams;
    }

    public void setRESOLVEToolParams(@NotNull String params) {
        resolveToolParams = params;
    }

    public void setWorkingDirectory(@NotNull String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
