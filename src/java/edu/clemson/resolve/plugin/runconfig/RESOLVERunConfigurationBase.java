package edu.clemson.resolve.plugin.runconfig;

import org.jetbrains.annotations.NotNull;

/**
 * Created by daniel on 7/11/15.
 */
public abstract class RESOLVERunConfigurationBase<RunningState> {
    @NotNull private String myWorkingDirectory = "";

    @NotNull
    public String getWorkingDirectory() {
        return myWorkingDirectory;
    }

    public void setWorkingDirectory(@NotNull String workingDirectory) {
        myWorkingDirectory = workingDirectory;
    }
}
