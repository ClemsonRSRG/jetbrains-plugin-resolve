package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;

public abstract class RESOLVEConfigurationFactoryBase
        extends
            ConfigurationFactory {

    protected RESOLVEConfigurationFactoryBase(ConfigurationType type) {
        super(type);
    }
}
