package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.TextFieldWithAutoCompletion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class RESOLVECommandConfigureDialog extends DialogWrapper {

    private static final Collection<String> PREDEFINED_COMMANDS =
            Arrays.asList("-genCode Java");

    private final TextFieldWithAutoCompletion<String> myCommandTextField;

    public RESOLVECommandConfigureDialog(@NotNull Project project) {
        super(project);
        setTitle("Edit RESOLVE Command Task");
        setModal(true);
        myCommandTextField = TextFieldWithAutoCompletion.create(project, PREDEFINED_COMMANDS, false, null);
        init();
    }

    @Nullable @Override protected ValidationInfo doValidate() {
        final String command = getCommand();
        if (command.isEmpty()) {
            return new ValidationInfo("Empty RESOLVE command is not allowed",
                    myCommandTextField);
        }
        return super.doValidate();
    }

    @Nullable @Override public JComponent getPreferredFocusedComponent() {
        return myCommandTextField;
    }

    @Nullable @Override protected JComponent createCenterPanel() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(myCommandTextField, BorderLayout.NORTH);
        return panel;
    }

    @NotNull public String getCommand() {
        return myCommandTextField.getText().trim();
    }
}
