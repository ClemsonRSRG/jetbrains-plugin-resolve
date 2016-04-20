package edu.clemson.resolve.jetbrains.project;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import org.jetbrains.annotations.NotNull;

@State(
        name = RESOLVEConstants.RESOLVE_LIBRARIES_SERVICE_NAME,
        storages = @Storage(file = StoragePathMacros.MODULE_FILE)
)
public class RESOLVEModuleLibrariesService
        extends
            RESOLVELibrariesService<RESOLVELibrariesState> {

    public static RESOLVEModuleLibrariesService getInstance(
            @NotNull Module module) {
        return ModuleServiceManager.getService(module,
                RESOLVEModuleLibrariesService.class);
    }
}
