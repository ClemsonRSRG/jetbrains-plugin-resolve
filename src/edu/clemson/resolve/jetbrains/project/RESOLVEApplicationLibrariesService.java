package edu.clemson.resolve.jetbrains.project;

/**
 * Created by daniel on 4/26/16.
 */
public class RESOLVEApplicationLibrariesService extends
        RESOLVELibrariesService<RESOLVEApplicationLibrariesService
                .RESOLVEApplicationLibrariesState> {

    public static class GoApplicationLibrariesState extends RESOLVELibrariesState {
        private boolean myUseGoPathFromSystemEnvironment = true;

        public boolean isUseGoPathFromSystemEnvironment() {
            return myUseGoPathFromSystemEnvironment;
        }

        public void setUseGoPathFromSystemEnvironment(boolean useGoPathFromSystemEnvironment) {
            myUseGoPathFromSystemEnvironment = useGoPathFromSystemEnvironment;
        }
    }
}
