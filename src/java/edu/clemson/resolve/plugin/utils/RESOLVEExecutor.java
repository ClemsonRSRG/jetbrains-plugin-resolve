package edu.clemson.resolve.plugin.utils;

import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessListener;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by daniel on 7/11/15.
 */
public class RESOLVEExecutor {

    @NotNull
    private final ParametersList myParameterList = new ParametersList();
    @NotNull private ProcessOutput myProcessOutput = new ProcessOutput();
    @NotNull private final Project myProject;
    @Nullable
    private final Module myModule;
    @Nullable private String myGoRoot;
    @Nullable private String myGoPath;
    @Nullable private String myEnvPath;
    @Nullable private String myWorkDirectory;
    private boolean myShowOutputOnError = false;
    private boolean myShowNotificationsOnError = false;
    private boolean myShowNotificationsOnSuccess = false;
    private boolean myPassParentEnvironment = true;
    private boolean myPtyDisabled = false;
    @Nullable private String myExePath = null;
    @Nullable private String myPresentableName;
    private OSProcessHandler myProcessHandler;
    private Collection<ProcessListener> myProcessListeners = ContainerUtil.newArrayList();

    private RESOLVEExecutor(@NotNull Project project, @Nullable Module module) {
        myProject = project;
        myModule = module;
    }

}
