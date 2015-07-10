package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RESOLVELanguageModuleType
        extends ModuleType<RESOLVELanguageModuleBuilder> {

    public static final String MODULE_TYPE_ID = "RESOLVE_LANGUAGE_MODULE";

    public RESOLVELanguageModuleType() {
        super(MODULE_TYPE_ID);
    }

    public static RESOLVELanguageModuleType getInstance() {
        return (RESOLVELanguageModuleType) ModuleTypeManager.getInstance()
                .findByID(MODULE_TYPE_ID);
    }

    @NotNull @Override public RESOLVELanguageModuleBuilder createModuleBuilder() {
        return new RESOLVELanguageModuleBuilder();
    }

    @NotNull @Override public String getName() {
        return "RESOLVE Module";
    }

    @NotNull @Override public String getDescription() {
        return "RESOLVE modules are used for developing and grouping RESOLVE" +
                "specifications and implementations.";
    }

    @Override public Icon getBigIcon() {
        return RESOLVEIcons.MODULE;
    }

    @Override public Icon getNodeIcon(@Deprecated boolean b) {
        return RESOLVEIcons.MODULE;
    }
}
