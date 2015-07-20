package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.util.Key;
import edu.clemson.resolve.plugin.runconfig.before.RESOLVEToolBeforeRunTask;
import edu.clemson.resolve.plugin.runconfig.before.RESOLVEToolBeforeRunTaskProvider;

public abstract class RESOLVEConfigurationFactoryBase
        extends
            ConfigurationFactory {

    protected RESOLVEConfigurationFactoryBase(ConfigurationType type) {
        super(type);
    }

    @Override public void configureBeforeRunTaskDefaults(
            Key<? extends BeforeRunTask> providerID, BeforeRunTask task) {
        if (!providerID.equals(RESOLVEToolBeforeRunTaskProvider.ID)) {
            task.setEnabled(false);
        }
    }
}
