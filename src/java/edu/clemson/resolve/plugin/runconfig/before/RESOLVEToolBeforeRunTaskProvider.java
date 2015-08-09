package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.tools.*;
import com.intellij.xml.XmlCoreEnvironment;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkService;
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
        return false;
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
        String outDir = workDir.getAbsolutePath() + File.separator + "out";
        if (StringUtil.isNotEmpty(config.getOutputFilePath())) {
            outDir = config.getOutputFilePath();
        }
        File outDirAsFile = new File(outDir);
        if (!outDirAsFile.exists()) {
            outDirAsFile.mkdir();
        }
        x.setParameters(
                "-jar resolve-0.0.1-complete.jar "
                        + fileToCompile + " -lib "
                        + config.getWorkingDirectory() + " -o "
                        + outDir + " -genCode Java -jar");
        x.setWorkingDirectory("/usr/local/resolve/tool");

        return !task.isExecutable()?false:task.execute(context, env.getExecutionId());
    }
}
