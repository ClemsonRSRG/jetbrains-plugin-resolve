package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.openapi.project.Project;

/**
 * Created by daniel on 7/12/15.
 */
public class RESOLVEModuleBasedConfiguration extends RunConfigurationModule {

    public RESOLVEModuleBasedConfiguration(Project project) {
        super(project);
    }
}
