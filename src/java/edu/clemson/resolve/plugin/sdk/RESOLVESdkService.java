package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class RESOLVESdkService extends SimpleModificationTracker {
    @NotNull protected final Project project;

    protected RESOLVESdkService(@NotNull Project project) {
        this.project = project;
    }

    public static RESOLVESdkService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RESOLVESdkService.class);
    }

    @Nullable public abstract String getSdkHomePath(@Nullable Module module);

    @NotNull public static String libraryRootToSdkPath(
            @NotNull VirtualFile root) {
        throw new UnsupportedOperationException("haven't implemented " +
                "library root to sdk path yet");
    }

    @Nullable public abstract String getSdkVersion(@Nullable Module module);

    public abstract void chooseAndSetSdk(@Nullable Module module);

    /**
     * Use this method in order to check whether the method is appropriate for
     * providing RESOLVE code insight.
     */
    @Contract("null -> false")
    public boolean isRESOLVEModule(@Nullable Module module) {
        return module != null && !module.isDisposed();
    }

    @Nullable public Configurable createSdkConfigurable() {
    return null;
    }

    @NotNull public String getRESOLVEToolJarPath(@Nullable Module module) {
        return getRESOLVEToolJarPath(getSdkHomePath(module));
    }

    public static String getRESOLVEToolJarPath(@Nullable String sdkHomePath) {
        File toolDirectory = new File(sdkHomePath, "tool");
        String jarName = "resolve-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
        return FileUtil.join(sdkHomePath, "tool", jarName);
    }
}
