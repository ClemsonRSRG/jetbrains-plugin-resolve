package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

public abstract class RESOLVESdkService extends SimpleModificationTracker {

    static final Logger LOG = Logger.getInstance(RESOLVESdkService.class);

    @NotNull
    final Project project;
    private static String dummyTestSdkVersion;

    protected RESOLVESdkService(@NotNull Project project) {
        this.project = project;
    }

    public static RESOLVESdkService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RESOLVESdkService.class);
    }

    @Nullable
    public abstract String getSdkHomePath(@Nullable Module module);

    @Nullable
    public abstract String getSdkCompilerJarPath(@Nullable Module module);

    //TODO TODO: This isn't right anymore
    @NotNull
    static String libraryRootToSdkPath(@NotNull VirtualFile root) {
        return VfsUtilCore.urlToPath(StringUtil.trimEnd(
                StringUtil.trimEnd(StringUtil.trimEnd(root.getUrl(),
                        "src/pkg"), "src"), "/"));
    }

    @Nullable
    public String getSdkVersion(@Nullable Module module) {
        return dummyTestSdkVersion;
    }

    public abstract void chooseAndSetSdk(@Nullable Module module);

    /**
     * Use this method in order to check whether the method is appropriate for providing RESOLVE-specific code insight
     */
    @Contract("null -> false")
    public boolean isRESOLVEModule(@Nullable Module module) {
        return module != null && !module.isDisposed();
    }
}
