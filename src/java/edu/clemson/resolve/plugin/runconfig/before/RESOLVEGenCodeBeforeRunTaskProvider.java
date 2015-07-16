package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.Nullable;

public class RESOLVEGenCodeBeforeRunTaskProvider
        extends
            BeforeRunTaskProvider<RESOLVEGenCodeBeforeRunTask> {

    public static final Key<RESOLVEGenCodeBeforeRunTask> ID =
            Key.create("RESOLVEGenCodeBeforeRunTask");

    @Override public Key<RESOLVEGenCodeBeforeRunTask> getId() {
        return ID;
    }

    @Override public String getName() {
        return "RESOLVE gencode";
    }

    @Override public String getDescription(RESOLVEGenCodeBeforeRunTask task) {
        return null;
    }

    @Override public boolean isConfigurable() {
        return false;
    }

    @Nullable @Override public RESOLVEGenCodeBeforeRunTask createTask(
            RunConfiguration runConfiguration) {
        return null;
    }

    @Override public boolean configureTask(RunConfiguration runConfiguration,
                                           RESOLVEGenCodeBeforeRunTask task) {
        return false;
    }

    @Override public boolean canExecuteTask(RunConfiguration configuration,
                                            RESOLVEGenCodeBeforeRunTask task) {
        return false;
    }

    @Override public boolean executeTask(DataContext context,
         RunConfiguration configuration, ExecutionEnvironment env,
         RESOLVEGenCodeBeforeRunTask task) {
        return false;
    }
}
