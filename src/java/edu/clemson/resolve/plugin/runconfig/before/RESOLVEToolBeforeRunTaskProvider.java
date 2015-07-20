package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.util.Key;
import com.intellij.tools.*;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEToolBeforeRunTaskProvider
        extends
            AbstractToolBeforeRunTaskProvider<RESOLVEToolBeforeRunTask> {

    public static final Key<RESOLVEToolBeforeRunTask> ID =
            Key.create("RESOLVEToolBeforeRunTask");

    @Override protected BaseToolsPanel createToolsPanel() {
        return new ToolsPanel();
    }

    @Override public Key<RESOLVEToolBeforeRunTask> getId() {
        return ID;
    }

    @Override public String getName() {
        return "RESOLVE Tool";
    }

    @Nullable @Override public Icon getIcon() {
        return RESOLVEIcons.APPLICATION_RUN;
    }

    @Nullable @Override public Icon getTaskIcon(
            RESOLVEToolBeforeRunTask task) {
        return getIcon();
    }

    @Override public boolean configureTask(RunConfiguration runConfiguration,
                                 RESOLVEToolBeforeRunTask task) {
        super.configureTask(runConfiguration, task);
        Tool x = task.findCorrespondingTool();
        assert x != null;
        x.setParameters("-jar resolve-0.0.1-SNAPSHOT-jar-with-dependencies.jar $");
        return true;
    }

    @Override public boolean isConfigurable() {
        return false;
    }

    @Nullable @Override public RESOLVEToolBeforeRunTask createTask(
            RunConfiguration runConfiguration) {
        return new RESOLVEToolBeforeRunTask();
    }
}
