package edu.clemson.resolve.plugin.runconfig.application;

import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import edu.clemson.resolve.plugin.psi.ResolveFile;
import edu.clemson.resolve.plugin.runconfig.RESOLVEModuleBasedConfiguration;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationBase;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunUtil;
import edu.clemson.resolve.plugin.runconfig.ui.RESOLVEApplicationConfigurationEditorForm;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RESOLVEApplicationConfiguration
        extends
        RESOLVERunConfigurationBase {

    public RESOLVEApplicationConfiguration(Project project, String name,
                           @NotNull ConfigurationType configurationType) {
        super(name, new RESOLVEModuleBasedConfiguration(project),
                configurationType.getConfigurationFactories()[0]);
    }

    @Override public Collection<Module> getValidModules() {
        return RESOLVESdkUtil.getRESOLVEModules(getProject());
    }

    @NotNull @Override public SettingsEditor<? extends RunConfiguration>
            getConfigurationEditor() {
        return new RESOLVEApplicationConfigurationEditorForm(getProject());
    }

    @NotNull @Override protected JavaCommandLineState newCommandLineState(
            ExecutionEnvironment env, Module module) {
        return new RESOLVEApplicationCommandLineState(this, env);
    }

    @Override public void checkConfiguration() throws RuntimeConfigurationException {
        super.checkConfiguration();
        VirtualFile file = VirtualFileManager.getInstance()
                .findFileByUrl(VfsUtilCore.pathToUrl(getFilePath()));
        if (file == null) {
            throw new RuntimeConfigurationError("RESOLVE file not specified");
        }
        PsiFile psiFile = PsiManager.getInstance(getProject()).findFile(file);
        if (psiFile == null || !(psiFile instanceof ResolveFile)) {
            throw new RuntimeConfigurationError("RESOLVE file is invalid");
        }
        if (!RESOLVERunUtil.isMainRESOLVEFile(psiFile)) {
            throw new RuntimeConfigurationError("Non executable module " +
                    "specified; must be a facility with a main function");
        }
    }


}
