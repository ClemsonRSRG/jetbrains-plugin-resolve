package edu.clemson.resolve.jetbrains.project;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import org.jetbrains.annotations.NotNull;

@State(
        name = RESOLVEConstants.RESOLVE_LIBRARIES_SERVICE_NAME,
        storages = {
            @Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
            @Storage(id = "dir", file = StoragePathMacros.PROJECT_CONFIG_DIR +
                    "/" + RESOLVEConstants.RESOLVE_LIBRARIES_CONFIG_FILE,
                    scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class RESOLVEProjectLibrariesService
        extends
        RESOLVELibrariesService<RESOLVELibrariesState> {
    public static RESOLVEProjectLibrariesService getInstance(
            @NotNull Project project) {
        return ServiceManager.getService(project,
                RESOLVEProjectLibrariesService.class);
    }
}