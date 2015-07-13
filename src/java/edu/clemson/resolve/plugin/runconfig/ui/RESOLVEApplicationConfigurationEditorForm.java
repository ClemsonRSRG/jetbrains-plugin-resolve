package edu.clemson.resolve.plugin.runconfig.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import edu.clemson.resolve.plugin.runconfig.RESOLVEApplicationConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVEApplicationConfigurationEditorForm
        extends
            SettingsEditor<RESOLVEApplicationConfiguration> {

    @NotNull private final Project project;

    public RESOLVEApplicationConfigurationEditorForm(
            @NotNull final Project project) {
        super(null);
        this.project = project;
        myCommonSettingsPanel.init(project);

        installRunKindComboBox();
        GoRunUtil.installGoWithMainFileChooser(myProject, myFileField);
        GoRunUtil.installFileChooser(myProject, myOutputFilePathField, true);
    }

    @Override protected void resetEditorFrom(Object o) {

    }

    @Override protected void applyEditorTo(Object o)
            throws ConfigurationException {

    }

    @Override
    protected void resetEditorFrom(RESOLVEApplicationConfiguration resolveApplicationConfiguration) {

    }

    @Override
    protected void applyEditorTo(RESOLVEApplicationConfiguration resolveApplicationConfiguration) throws ConfigurationException {

    }

    @NotNull @Override protected JComponent createEditor() {
        return null;
    }


}
