package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.psi.RESOLVEPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RESOLVERunConfigurationProducerBase
            <T extends RESOLVERunConfigurationBase>
        extends
            RunConfigurationProducer<T> implements Cloneable {

    protected RESOLVERunConfigurationProducerBase(
            @NotNull ConfigurationType configurationType) {
        super(configurationType);
    }

    @Override protected boolean setupConfigurationFromContext(T configuration,
              ConfigurationContext configurationContext, Ref<PsiElement> ref) {
        PsiFile file = getFileFromContext(configurationContext);
        if (file == null) return false;
        //if (RESOLVERunUtil.isMainRESOLVEFacilityFile(file)) {
        configuration.setName(getConfigurationName(file));
        configuration.setFilePath(file.getVirtualFile().getPath());
        Module module = configurationContext.getModule();
        if (module != null) configuration.setModule(module);
        return true;
        //}
        //return false;
    }

    @NotNull protected abstract String getConfigurationName(
            @NotNull PsiFile file);

    @Override public boolean isConfigurationFromContext(T configuration,
                                ConfigurationContext configurationContext) {
        RESOLVEPsiFile file = getFileFromContext(configurationContext);
        return file != null && FileUtil.pathsEqual(configuration.getFilePath(),
                file.getVirtualFile().getPath());
    }

    @Nullable private static RESOLVEPsiFile getFileFromContext(
            @Nullable ConfigurationContext context) {
        PsiElement contextElement = RESOLVERunUtil.getContextElement(context);
        PsiFile psiFile = contextElement != null ?
                contextElement.getContainingFile() : null;
        return psiFile != null && psiFile instanceof RESOLVEPsiFile ?
                ((RESOLVEPsiFile)psiFile) : null;
    }
}