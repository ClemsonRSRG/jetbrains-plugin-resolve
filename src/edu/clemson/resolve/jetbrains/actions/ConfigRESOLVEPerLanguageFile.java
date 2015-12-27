package edu.clemson.resolve.jetbrains.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

public class ConfigRESOLVEPerLanguageFile extends DialogWrapper {

    public static final String PROP_OUTPUT_DIR = "output-dir";
    //public static final String PROP_PACKAGE = "package";

    private JPanel dialogContents;

    private TextFieldWithBrowseButton outputDirField;
    private TextFieldWithBrowseButton libDirField;
    private JCheckBox genVCsCheckBox;

    public ConfigRESOLVEPerLanguageFile(final Project project,
                                        String qualFileName) {
        super(project, false);
        init();
        FileChooserDescriptor dirChooser =
                FileChooserDescriptorFactory.createSingleFolderDescriptor();
        outputDirField.addBrowseFolderListener("Select Output Dir", null,
                project, dirChooser);
        outputDirField.setTextFieldPreferredWidth(50);
        loadValues(project, qualFileName);
    }

    public static String getOutputDirName(Project project, String qualFileName,
                                          VirtualFile contentRoot) {
        String outputDirName = contentRoot.getPath()+File.separator
                + RunRESOLVEOnLanguageFile.OUTPUT_DIR_NAME;
        outputDirName = getProp(project, qualFileName, PROP_OUTPUT_DIR, outputDirName);
        return outputDirName;
    }

    public static String getProp(Project project, String qualFileName,
                                 String name,
                                 String defaultValue) {
        PropertiesComponent props = PropertiesComponent.getInstance(project);
        String v = props.getValue(getPropNameForFile(qualFileName, name));
        if ( v==null || v.trim().length()==0 ) return defaultValue;
        return v;
    }

    public static boolean getBooleanProp(Project project, String qualFileName,
                                         String name, boolean defaultValue) {
        PropertiesComponent props = PropertiesComponent.getInstance(project);
        return props.getBoolean(getPropNameForFile(qualFileName, name), defaultValue);
    }

    public static String getParentDir(VirtualFile vfile) {
        return vfile.getParent().getPath();
    }

    public static VirtualFile getContentRoot(Project project, VirtualFile vfile) {
        VirtualFile root =
                ProjectRootManager.getInstance(project)
                        .getFileIndex().getContentRootForFile(vfile);
        if ( root!=null ) return root;
        return vfile.getParent();
    }

    public void loadValues(Project project, String qualFileName) {
        PropertiesComponent props = PropertiesComponent.getInstance(project);
        String s = props.getValue(getPropNameForFile(qualFileName, PROP_OUTPUT_DIR), "");
        outputDirField.setText(s);
        //s = props.getValue(getPropNameForFile(qualFileName, PROP_PACKAGE), "");
        //packageField.setText(s);
    }

    public void saveValues(Project project, String qualFileName) {
        String v;
        PropertiesComponent props = PropertiesComponent.getInstance(project);

        v = outputDirField.getText();
        if ( v.trim().length()>0 ) {
            props.setValue(getPropNameForFile(qualFileName, PROP_OUTPUT_DIR), v);
        }
        else {
            props.unsetValue(getPropNameForFile(qualFileName, PROP_OUTPUT_DIR));
        }

        /*v = packageField.getText();
        if ( v.trim().length()>0 ) {
            props.setValue(getPropNameForFile(qualFileName, PROP_PACKAGE), v);
        }
        else {
            props.unsetValue(getPropNameForFile(qualFileName, PROP_PACKAGE));
        }*/
    }

    public static String getPropNameForFile(String qualFileName, String prop) {
        return qualFileName+"::/"+prop;
    }

    @Nullable @Override protected JComponent createCenterPanel() {
        return dialogContents;
    }

    @Override public String toString() {
        return "ConfigRESOLVEPerLanguageFile{"+
                "textField2="+
               // "  packageField="+packageField+
                "  outputDirField="+outputDirField+
                '}';
    }
}
