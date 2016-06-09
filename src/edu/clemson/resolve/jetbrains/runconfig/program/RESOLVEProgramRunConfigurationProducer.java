package edu.clemson.resolve.jetbrains.runconfig.program;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.runconfig.RESOLVERunUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEProgramRunConfigurationProducer
        extends
        RunConfigurationProducer<RESOLVEProgramRunConfiguration> implements Cloneable {

    protected RESOLVEProgramRunConfigurationProducer(@NotNull ConfigurationType configurationType) {
        super(configurationType);
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull RESOLVEProgramRunConfiguration configuration,
                                                    @NotNull ConfigurationContext context,
                                                    Ref<PsiElement> sourceElement) {
        PsiFile file = getFileFromContext(context);
        if (RESOLVERunUtil.isMainRESOLVEFile(file)) {
            configuration.setName(getConfigurationName(file));
            configuration.setFilePath(file.getVirtualFile().getPath());
            Module module = context.getModule();
            if (module != null) {
                configuration.setModule(module);
            }
            return true;
        }
        return false;
    }

    @NotNull
    protected String getConfigurationName(@NotNull PsiFile file) {
        return "RESOLVE run " + file.getName();
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull RESOLVEProgramRunConfiguration configuration,
                                              ConfigurationContext context) {
        ResFile file = getFileFromContext(context);
        return file != null && FileUtil.pathsEqual(configuration.getFilePath(), file.getVirtualFile().getPath());
    }

    @Nullable
    private static ResFile getFileFromContext(@Nullable ConfigurationContext context) {
        PsiElement contextElement = RESOLVERunUtil.getContextElement(context);
        PsiFile psiFile = contextElement != null ? contextElement.getContainingFile() : null;
        return psiFile instanceof ResFile ? (ResFile)psiFile : null;
    }
}
