package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.rt.compiler.JavacRunner;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEBeforeRunTaskProvider
        extends
            BeforeRunTaskProvider<RESOLVECommandBeforeRunTask> {

    public static final Key<RESOLVECommandBeforeRunTask> ID =
            Key.create("RESOLVEBeforeRunTask");

    @Override public Key<RESOLVECommandBeforeRunTask> getId() {
        return ID;
    }

    @Override public String getName() {
        return "RESOLVE command";
    }

    @Override public String getDescription(RESOLVECommandBeforeRunTask task) {
        return "run `" + task.toString() + "`";
    }

    @Nullable @Override public Icon getIcon() {
        return RESOLVEIcons.APPLICATION_RUN;
    }

    @Nullable @Override public Icon getTaskIcon(
            RESOLVECommandBeforeRunTask task) {
        return getIcon();
    }

    @Override public boolean isConfigurable() {
        return false;
    }

    @Nullable @Override public RESOLVECommandBeforeRunTask createTask(
            RunConfiguration runConfiguration) {
        return runConfiguration instanceof RESOLVERunConfigurationBase ?
                new RESOLVECommandBeforeRunTask() : null;
    }

    @Override public boolean configureTask(RunConfiguration runConfiguration,
                                           RESOLVECommandBeforeRunTask task) {
        final Project project = runConfiguration.getProject();
        if (!(runConfiguration instanceof RESOLVERunConfigurationBase)) {
            showAddingTaskErrorMessage(project,
                    "RESOLVE code generation only works on RESOLVE " +
                            "run configurations");
            return false;
        }

        Module module = ((RESOLVERunConfigurationBase)runConfiguration)
                .getConfigurationModule().getModule();
        if (!RESOLVESdkService.getInstance(project).isRESOLVEModule(module)) {
            showAddingTaskErrorMessage(project, "RESOLVE code generation " +
                    "only supported in RESOLVE modules");
            return false;
        }
        RESOLVECommandConfigureDialog dialog =
                new RESOLVECommandConfigureDialog(project);
        if (dialog.showAndGet()) {
            task.setCommand(dialog.getCommand());
            return true;
        }
        return false;
    }

    @Override public boolean canExecuteTask(RunConfiguration configuration,
                                            RESOLVECommandBeforeRunTask task) {
        if (configuration instanceof RESOLVERunConfigurationBase) {
            Module module = ((RESOLVERunConfigurationBase)configuration)
                    .getConfigurationModule().getModule();
            RESOLVESdkService sdkService = RESOLVESdkService.getInstance(
                    configuration.getProject());
            if (sdkService.isRESOLVEModule(module)) {
                return StringUtil.isNotEmpty(sdkService.getSdkHomePath(module))
                        && StringUtil.isNotEmpty(task.getCommand());
            }
        }
        return false;
    }

    @Override public boolean executeTask(DataContext context,
         RunConfiguration configuration, ExecutionEnvironment env,
         RESOLVECommandBeforeRunTask task) {
     //   Runner
        return false;
    }

    private static void showAddingTaskErrorMessage(final Project project,
                                                   final String message) {
        Messages.showErrorDialog(project, message, "RESOLVE Code Generation Task");
    }
}
