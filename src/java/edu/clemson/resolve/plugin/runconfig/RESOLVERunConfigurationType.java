package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import edu.clemson.resolve.plugin.RESOLVEIcons;

import javax.swing.*;

public class RESOLVERunConfigurationType extends ConfigurationTypeBase {

    protected RESOLVERunConfigurationType() {
        super("RESOLVERunFileConfiguration", "RESOLVE File",
                "RESOLVE source file run configuration",
                RESOLVEIcons.APPLICATION_RUN);
        addFactory(new RESOLVEConfigurationFactoryBase(this) {

            @Override public RunConfiguration createTemplateConfiguration(
                    Project project) {
                return null;
            }
        });
    }
}
