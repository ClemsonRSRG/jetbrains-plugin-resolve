package edu.clemson.resolve.plugin.sdk;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.NotNull;

public class RESOLVELanguageModuleBuilder
        extends
            JavaModuleBuilder implements
                SourcePathsBuilder, ModuleBuilderListener {

    @Override public void setupRootModel(ModifiableRootModel rootModel)
            throws ConfigurationException {
        addListener(this);
        super.setupRootModel(rootModel);
    }

    /**
     * Returns the RESOLVE Language module type.
     */
    @Override public ModuleType getModuleType() {
        return RESOLVELanguageModuleType.getInstance();
    }

    /**
     * Ensures that SDK type is a D Language SDK.
     */
    @Override public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType == RESOLVELanguageSdkType.getInstance();
    }

    /**
     * Called after module is created.
     */
    @Override public void moduleCreated(@NotNull Module module) {
        // create the dub project
       /* createDub(module.getProject().getBaseDir().getCanonicalPath());

        // Update the ignored files and folders to avoid file search showing compiled files.
        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        StringBuilder builder = new StringBuilder(fileTypeManager.getIgnoredFilesList());
        // Ensure the ignored file list ends with a semicolon.
        if (builder.charAt(builder.length() - 1) != ';') {
            builder.append(';');
        }
        for (String ignore : new String[]{"*.dyn_hi", "*.dyn_hi", "*.dyn_o", "*.hi", "*.o"}) {
            if (builder.indexOf(';' + ignore + ';') == -1) {
                builder.append(ignore).append(';');
            }
        }
        fileTypeManager.setIgnoredFilesList(builder.toString());*/
    }
}
