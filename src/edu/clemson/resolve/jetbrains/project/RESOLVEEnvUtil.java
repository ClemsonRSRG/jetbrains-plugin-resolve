package edu.clemson.resolve.jetbrains.project;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathMacros;
import com.intellij.util.EnvironmentUtil;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import org.jetbrains.annotations.Nullable;

public class RESOLVEEnvUtil {

    @Nullable
    public static String retrieveRESOLVEPathFromEnvironment() {
        if (ApplicationManager.getApplication().isUnitTestMode()) return null;

        String path = EnvironmentUtil.getValue(RESOLVEConstants.RESOLVE_PATH);
        return path != null ? path : PathMacros.getInstance().getValue(RESOLVEConstants.RESOLVE_PATH);
    }
}
