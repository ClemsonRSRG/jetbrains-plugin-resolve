package edu.clemson.resolve.plugin.runconfig.application;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.runconfig.RESOLVEConfigurationFactoryBase;
import org.jetbrains.annotations.NotNull;

public class RESOLVEApplicationRunConfigurationType
        extends
            ConfigurationTypeBase {

    protected RESOLVEApplicationRunConfigurationType() {
        super("RESOLVEApplicationRunConfiguration", "RESOLVE File",
                "RESOLVE source run configuration",
                RESOLVEIcons.APPLICATION_RUN);
        addFactory(new RESOLVEConfigurationFactoryBase(this) {

            @Override public RunConfiguration createTemplateConfiguration(
                    Project project) {
                return new RESOLVEApplicationConfiguration(
                        project, "RESOLVE", getInstance());
            }
        });
    }

    @NotNull public static RESOLVEApplicationRunConfigurationType getInstance() {
        return Extensions.findExtension(CONFIGURATION_TYPE_EP,
                RESOLVEApplicationRunConfigurationType.class);
    }
}
