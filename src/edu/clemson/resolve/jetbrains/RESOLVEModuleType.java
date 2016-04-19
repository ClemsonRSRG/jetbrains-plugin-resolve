package edu.clemson.resolve.jetbrains;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.security.pkcs11.Secmod;

import javax.swing.*;

public class RESOLVEModuleType extends ModuleType<RESOLVEModuleBuilder> {

    public RESOLVEModuleType() {
        super(RESOLVEConstants.MODULE_TYPE_ID);
    }

    @NotNull public static RESOLVEModuleType getInstance() {
        return (RESOLVEModuleType) ModuleTypeManager.getInstance()
                .findByID(RESOLVEConstants.MODULE_TYPE_ID);
    }

    @NotNull @Override public RESOLVEModuleBuilder createModuleBuilder() {
        return new RESOLVEModuleBuilder();
    }

    @NotNull @Override public String getName() {
        return "RESOLVE Module";
    }

    @NotNull @Override public String getDescription() {
        return "RESOLVE modules are used for developing <b>RESOLVE</b> " +
                "component based software.";
    }

    @Nullable @Override public Icon getBigIcon() {
        return RESOLVEIcons.MODULE;
    }

    @Nullable @Override public Icon getNodeIcon(boolean isOpened) {
        return RESOLVEIcons.TOOL_ICON;
    }

    @NotNull @Override public ModuleWizardStep[] createWizardSteps(
            @NotNull WizardContext wizardContext,
            @NotNull final RESOLVEModuleBuilder moduleBuilder,
            @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new ProjectJdkForModuleStep(wizardContext, RESOLVESdkType.getInstance()) {
            @Override public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        }};
    }
}
