package edu.clemson.resolve.jetbrains.runconfig.file;

import com.intellij.compiler.options.CompileStepBeforeRun;
import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVEProgramRunConfigurationType extends ConfigurationTypeBase {

    public RESOLVEProgramRunConfigurationType() {
        super("RESOLVERunConfiguration", "RESOLVE Single Program", "RESOLVE single program configuration",
                RESOLVEIcons.PROGRAM_RUN);
        addFactory(new ConfigurationFactory(this) {
            @Override
            @NotNull
            public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
                return new RESOLVEProgramRunConfiguration(project, RESOLVEConstants.RESOLVE, getInstance());
            }

            @Override
            public void configureBeforeRunTaskDefaults(Key<? extends BeforeRunTask> providerID, BeforeRunTask task) {
                super.configureBeforeRunTaskDefaults(providerID, task);
                if (providerID == CompileStepBeforeRun.ID) {
                    task.setEnabled(false);
                }
            }
        });
    }
}
