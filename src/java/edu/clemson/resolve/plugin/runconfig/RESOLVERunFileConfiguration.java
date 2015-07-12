package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class RESOLVERunFileConfiguration
        extends
            ModuleBasedConfiguration<RESOLVEModuleBasedConfiguration> {

    public RESOLVERunFileConfiguration(String name,
               @NotNull RESOLVEModuleBasedConfiguration configurationModule,
               @NotNull ConfigurationFactory factory) {
        super(name, configurationModule, factory);
    }

    @Override public Collection<Module> getValidModules() {
        return null;
    }

    @NotNull @Override public SettingsEditor<? extends RunConfiguration>
            getConfigurationEditor() {
        return null;
    }

    @Nullable @Override public RunProfileState getState(
            @NotNull Executor executor,
            @NotNull ExecutionEnvironment environment)
            throws ExecutionException {
        return null;
    }
}
