package edu.clemson.resolve.plugin.runconfig.application;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunConfigurationProducerBase;
import edu.clemson.resolve.plugin.runconfig.RESOLVERunUtil;
import org.jetbrains.annotations.NotNull;

/**
 *
 * Created by daniel on 7/13/15.
 */
public class RESOLVEApplicationRunConfigurationProducer
        extends
            RESOLVERunConfigurationProducerBase<RESOLVEApplicationConfiguration> implements Cloneable {

    protected RESOLVEApplicationRunConfigurationProducer() {
        super(RESOLVEApplicationRunConfigurationType.getInstance());
    }

    @Override public boolean isConfigurationFromContext(
            @NotNull RESOLVEApplicationConfiguration configuration, ConfigurationContext context) {
        PsiElement contextElement = RESOLVERunUtil.getContextElement(context);
        if (contextElement == null) return false;

        Module module = ModuleUtilCore.findModuleForPsiElement(contextElement);
        if (!Comparing.equal(module, configuration.getConfigurationModule().getModule())) return false;

        return super.isConfigurationFromContext(configuration, context);
    }

    @NotNull @Override protected String getConfigurationName(
            @NotNull PsiFile file) {
        return "" + file.getName();
    }
}