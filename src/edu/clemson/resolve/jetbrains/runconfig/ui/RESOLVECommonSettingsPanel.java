package edu.clemson.resolve.jetbrains.runconfig.ui;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.configuration.EnvironmentVariablesTextFieldWithBrowseButton;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import edu.clemson.resolve.jetbrains.runconfig.RESOLVERunUtil;
import edu.clemson.resolve.jetbrains.runconfig.program.RESOLVEProgramRunConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVECommonSettingsPanel extends JPanel {
  private RawCommandLineEditor myGoToolParamsField;
  private RawCommandLineEditor myParamsField;
  private TextFieldWithBrowseButton myWorkingDirectoryField;
  private EnvironmentVariablesTextFieldWithBrowseButton myEnvironmentField;
  private ModulesComboBox myModulesComboBox;
  @SuppressWarnings("unused") private JPanel myRoot;

  public void init(@NotNull Project project) {
    RESOLVERunUtil.installFileChooser(project, myWorkingDirectoryField, true);
    myGoToolParamsField.setDialogCaption("RESOLVE tool arguments");
    myParamsField.setDialogCaption("Program arguments");
  }

  public void resetEditorFrom(@NotNull RESOLVEProgramRunConfiguration configuration) {
    myModulesComboBox.setModules(configuration.getValidModules());
    myModulesComboBox.setSelectedModule(configuration.getConfigurationModule().getModule());
   // myGoToolParamsField.setText(configuration.getGoToolParams());
    //myParamsField.setText(configuration.getParams());
    myWorkingDirectoryField.setText(configuration.getWorkingDirectory());
   // myEnvironmentField.setEnvs(configuration.getCustomEnvironment());
   // myEnvironmentField.setPassParentEnvs(configuration.isPassParentEnvironment());
  }

  public void applyEditorTo(@NotNull RESOLVEProgramRunConfiguration configuration) {
    configuration.setModule(myModulesComboBox.getSelectedModule());
    //configuration.setRESOLVEParams(myRESOLVEToolParamsField.getText());
    //configuration.setParams(myParamsField.getText());
    configuration.setWorkingDirectory(myWorkingDirectoryField.getText());
    //configuration.setCustomEnvironment(myEnvironmentField.getEnvs());
    //configuration.setPassParentEnvironment(myEnvironmentField.isPassParentEnvs());
  }

  @Nullable
  public Module getSelectedModule() {
    return myModulesComboBox.getSelectedModule();
  }
}
