package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigRESOLVEPerFile extends DialogWrapper {

    public ConfigRESOLVEPerFile(final Project project, String qualFileName) {
        super(project, false);
        init();

       /* FileChooserDescriptor dirChooser =
                FileChooserDescriptorFactory.createSingleFolderDescriptor();
        outputDirField.addBrowseFolderListener("Select output dir", null, project, dirChooser);
        outputDirField.setTextFieldPreferredWidth(50);

        dirChooser =
                FileChooserDescriptorFactory.createSingleFolderDescriptor();
        libDirField.addBrowseFolderListener("Select lib dir", null, project, dirChooser);
        libDirField.setTextFieldPreferredWidth(50);

        loadValues(project, qualFileName);*/
    }
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return null;
    }
}
