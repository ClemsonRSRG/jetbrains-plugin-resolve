package edu.clemson.resolve.jetbrains.project;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = RESOLVEConstants.RESOLVE_LIBRARIES_SERVICE_NAME,
        storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/" +
                RESOLVEConstants.RESOLVE_LIBRARIES_CONFIG_FILE)
)
public class RESOLVEApplicationLibrariesService
        extends
            RESOLVELibrariesService<RESOLVEApplicationLibrariesService
                    .RESOLVEApplicationLibrariesState> {

    @NotNull @Override protected RESOLVEApplicationLibrariesState createState() {
        return new RESOLVEApplicationLibrariesState();
    }

    public static RESOLVEApplicationLibrariesService getInstance() {
        return ServiceManager.getService(RESOLVEApplicationLibrariesService.class);
    }

    public boolean isUseGoPathFromSystemEnvironment() {
        return state.isUseGoPathFromSystemEnvironment();
    }

    public void setUseGoPathFromSystemEnvironment(boolean useGoPathFromSystemEnvironment) {
        if (state.isUseGoPathFromSystemEnvironment() != useGoPathFromSystemEnvironment) {
            state.setUseGoPathFromSystemEnvironment(useGoPathFromSystemEnvironment);
            if (!RESOLVESdkUtil.getRESOLVEPathRootsFromEnvironment().isEmpty()) {
                incModificationCount();
                ApplicationManager
                        .getApplication()
                        .getMessageBus()
                        .syncPublisher(LIBRARIES_TOPIC)
                        .librariesChanged(getLibraryRootUrls());
            }
        }
    }

    public static class RESOLVEApplicationLibrariesState
            extends
                RESOLVELibrariesState {
        private boolean useRESOLVEPathFromSystemEnvironment = true;

        public boolean isUseGoPathFromSystemEnvironment() {
            return useRESOLVEPathFromSystemEnvironment;
        }

        public void setUseGoPathFromSystemEnvironment(
                boolean useRESOLVEPath) {
            this.useRESOLVEPathFromSystemEnvironment = useRESOLVEPath;
        }
    }
}
