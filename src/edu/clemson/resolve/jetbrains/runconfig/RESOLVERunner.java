package edu.clemson.resolve.jetbrains.runconfig;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import edu.clemson.resolve.jetbrains.runconfig.program.RESOLVEProgramRunConfiguration;
import org.jetbrains.annotations.NotNull;

public class RESOLVERunner extends DefaultProgramRunner {
    private static final String ID = "RESOLVERunner";

    @NotNull
    @Override
    public String getRunnerId() {
    return ID;
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) && profile instanceof RESOLVEProgramRunConfiguration;
    }
}