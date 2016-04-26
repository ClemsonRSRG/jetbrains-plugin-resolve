package edu.clemson.resolve.jetbrains.project;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;

public class RESOLVEApplicationLibrariesService extends
        RESOLVELibrariesService<RESOLVEApplicationLibrariesService
                .RESOLVEApplicationLibrariesState> {

    @NotNull
    @Override
    protected RESOLVEApplicationLibrariesState createState() {
        return new RESOLVEApplicationLibrariesState();
    }

    public static RESOLVEApplicationLibrariesService getInstance() {
        return ServiceManager.getService(
                RESOLVEApplicationLibrariesService.class);
    }

    public boolean isUsingRESOLVEPathFromSystemEnvironment() {
        return state.isUseGoPathFromSystemEnvironment();
    }

    public void setUseGoPathFromSystemEnvironment(boolean useRESPATHfromEnv) {
        if ( state.isUseGoPathFromSystemEnvironment()!=useRESPATHfromEnv ) {
            state.setUseGoPathFromSystemEnvironment(useRESPATHfromEnv);
            if ( !RESOLVESdkUtil.getRESOLVEPathsRootsFromEnvironment()
                    .isEmpty() ) {
                incModificationCount();
                ApplicationManager.getApplication()
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
                boolean useResPathFromSysEnv) {
            useRESOLVEPathFromSystemEnvironment = useResPathFromSysEnv;
        }
    }
}
