package edu.clemson.resolve.plugin;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVEModuleType extends ModuleType<RESOLVEModuleBuilder> {

    public static final String MODULE_TYPE_ID = "RESOLVE_LANGUAGE_MODULE";

    public RESOLVEModuleType() {
        super(MODULE_TYPE_ID);
    }

    @NotNull public static RESOLVEModuleType getInstance() {
        return (RESOLVEModuleType) ModuleTypeManager.getInstance()
                .findByID(MODULE_TYPE_ID);
    }

    @NotNull @Override public RESOLVEModuleBuilder createModuleBuilder() {
        return new RESOLVEModuleBuilder();
    }

    @NotNull @Override public String getName() {
        return "RESOLVE Module";
    }

    @NotNull @Override public String getDescription() {
        return "RESOLVE modules are used for developing and grouping RESOLVE" +
                " specifications and implementations.";
    }

    @Override public Icon getBigIcon() {
        return RESOLVEIcons.MODULE;
    }

    @Override public Icon getNodeIcon(@Deprecated boolean b) {
        return RESOLVEIcons.MODULE;
    }

    @NotNull @Override public ModuleWizardStep[] createWizardSteps(
            @NotNull WizardContext wizardContext,
            @NotNull final RESOLVEModuleBuilder moduleBuilder,
            @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{new ProjectJdkForModuleStep(
                wizardContext, RESOLVESdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        }};
    }
}
