/*
 * Copyright 2013-2015 Sergey Ignatov, Alexander Zolotov, Florin Patan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
