package edu.clemson.resolve.plugin.runconfig.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import edu.clemson.resolve.plugin.runconfig.application.RESOLVEApplicationConfiguration;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVEApplicationConfigurationEditorForm
        extends
            SettingsEditor<RESOLVEApplicationConfiguration> {

    @NotNull private final Project project;
    private JPanel component;
    private JLabel fileLabel;
    private TextFieldWithBrowseButton fileField;
    private TextFieldWithBrowseButton outputDirectoryField;
    private JLabel outputDirectoryLabel;
    private RESOLVECommonSettingsPanel commonSettingsPanel;

    public RESOLVEApplicationConfigurationEditorForm(
            @NotNull final Project project) {
        super(null);
        this.project = project;
        this.commonSettingsPanel.init(project);
        RESOLVERunUtil.installRESOLVEWithMainFileChooser(this.project, this.fileField);
        //RESOLVERunUtil.installFileChooser(this.project, fileField, false);
        RESOLVERunUtil.installFileChooser(project, outputDirectoryField, true);
    }

    @Override protected void resetEditorFrom(
            @NotNull RESOLVEApplicationConfiguration configuration) {
        fileField.setText(configuration.getFilePath());
        outputDirectoryField.setText(StringUtil.notNullize(
                configuration.getOutputFilePath()));
        commonSettingsPanel.resetEditorFrom(configuration);
    }

    @Override protected void applyEditorTo(
            @NotNull RESOLVEApplicationConfiguration configuration)
            throws ConfigurationException {
        configuration.setFilePath(fileField.getText());
        configuration.setFileOutputPath(StringUtil.nullize(
                outputDirectoryField.getText()));
        commonSettingsPanel.applyEditorTo(configuration);
    }

    @NotNull @Override protected JComponent createEditor() {
        return component;
    }

    @Override protected void disposeEditor() {
        component.setVisible(false);
    }
}
