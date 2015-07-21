package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.tools.*;
import com.intellij.xml.XmlCoreEnvironment;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

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

    @Override public boolean isConfigurable() {
        return true;
    }

    @Nullable @Override public RESOLVEToolBeforeRunTask createTask(
            RunConfiguration runConfiguration) {
        return new RESOLVEToolBeforeRunTask();
    }

    @Override public boolean executeTask(DataContext context,
          RunConfiguration configuration, ExecutionEnvironment env,
                                         RESOLVEToolBeforeRunTask task) {
        Tool x = task.findCorrespondingTool();
        assert x != null;
        x.setProgram("java");

        if (!(configuration instanceof RESOLVERunConfigurationBase)) {
            throw new IllegalStateException("not a resolve run config?!");
        }
        RESOLVERunConfigurationBase config =
                (RESOLVERunConfigurationBase) configuration;
        File f = new File(config.getFilePath());
        File workDir = new File(config.getWorkingDirectory());
        String fileToCompile = FileUtil.getRelativePath(workDir, f);
        x.setParameters(
                "-jar resolve-0.0.1-SNAPSHOT-jar-with-dependencies.jar " + fileToCompile + " -lib "
                        + config.getWorkingDirectory() + " -o " + workDir.getAbsolutePath() + "/gen -genCode Java");
        x.setWorkingDirectory("/usr/local/lib");

        return !task.isExecutable()?false:task.execute(context, env.getExecutionId());
    }
}
