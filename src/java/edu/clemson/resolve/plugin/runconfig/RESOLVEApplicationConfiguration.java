package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
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
import edu.clemson.resolve.plugin.psi.RESOLVEPsiFile;
import edu.clemson.resolve.plugin.runconfig.ui.RESOLVEApplicationConfigurationEditorForm;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @NotNull @Override protected RESOLVEApplicationCommandLineState
                newCommandLineState(@NotNull ExecutionEnvironment env,
                                    @NotNull Module module) {
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
        if (psiFile == null || !(psiFile instanceof RESOLVEPsiFile)) {
            throw new RuntimeConfigurationError("RESOLVE file is invalid");
        }
     //   if (!RESOLVERunUtil.isMainGoFile(psiFile)) {
     //       throw new RuntimeConfigurationError("Main file has non-main package or doesn't contain main function");
     //   }
    }


}
