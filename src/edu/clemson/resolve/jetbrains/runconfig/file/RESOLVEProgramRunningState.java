package edu.clemson.resolve.jetbrains.runconfig.file;

import com.intellij.execution.configurations.CommandLineState;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;

public class RESOLVEProgramRunningState extends CommandLineState {

    @NotNull protected final RESOLVEProgramRunConfigurationType myConfiguration;

    @NotNull
    protected final Module myModule;

    @NotNull
    public T getConfiguration() {
        return myConfiguration;
    }
}
