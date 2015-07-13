package edu.clemson.resolve.plugin.runconfig.ui;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVECommonSettingsPanel extends JPanel {

    private RawCommandLineEditor resolveToolParamsField;
    private RawCommandLineEditor paramsField;
    private TextFieldWithBrowseButton workingDirectoryField;
    private ModulesComboBox modulesComboBox;
    @SuppressWarnings("unused") private JPanel root;

    public void init(@NotNull Project project) {
        RESOLVERunUtil.installFileChooser(project, workingDirectoryField, true);
        resolveToolParamsField.setDialogCaption("RESOLVE tool arguments");
        paramsField.setDialogCaption("Program arguments");
    }

    public void resetEditorFrom(
            @NotNull RESOLVERunConfigurationBase configuration) {
        modulesComboBox.setModules(configuration.getValidModules());
        modulesComboBox.setSelectedModule(configuration.getConfigurationModule().getModule());
        resolveToolParamsField.setText(configuration.getRESOLVEToolParams());
        workingDirectoryField.setText(configuration.getWorkingDirectory());
    }

    public void applyEditorTo(
            @NotNull RESOLVERunConfigurationBase configuration) {
        configuration.setModule(modulesComboBox.getSelectedModule());
        configuration.setRESOLVEToolParams(resolveToolParamsField.getText());
        configuration.setWorkingDirectory(workingDirectoryField.getText());
    }

    @Nullable public Module getSelectedModule() {
        return modulesComboBox.getSelectedModule();
    }
}
