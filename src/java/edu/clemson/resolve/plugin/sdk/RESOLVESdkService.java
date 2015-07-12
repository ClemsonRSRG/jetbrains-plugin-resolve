package edu.clemson.resolve.plugin.sdk;

import com.intellij.execution.configurations.PathEnvironmentVariableUtil;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PathUtil;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Set;

public abstract class RESOLVESdkService extends SimpleModificationTracker {
    @NotNull protected final Project project;

    protected RESOLVESdkService(@NotNull Project project) {
        this.project = project;
    }

    public static RESOLVESdkService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RESOLVESdkService.class);
    }

    @Nullable public abstract String getSdkHomePath(@Nullable Module module);

    @NotNull public static String libraryRootToSdkPath(@NotNull VirtualFile root) {
    return VfsUtilCore.urlToPath(StringUtil.trimEnd(
            StringUtil.trimEnd(StringUtil.trimEnd(root.getUrl(), "src/pkg"), "src"), "/"));
    }

    @Nullable public abstract String getSdkVersion(@Nullable Module module);

    public abstract void chooseAndSetSdk(@Nullable Module module);

    /**
    * Use this method in order to check whether the method is appropriate for
    * providing res code insight.
    */
    @Contract("null -> false")
    public boolean isRESOLVEModule(@Nullable Module module) {
        return module != null && !module.isDisposed();
    }

    @Nullable public Configurable createSdkConfigurable() {
    return null;
    }

    @Nullable public String getGoExecutablePath(@Nullable Module module) {
        return getGoExecutablePath(getSdkHomePath(module));
    }

    public static String getGoExecutablePath(@Nullable String sdkHomePath) {
        return sdkHomePath + "/resolve-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
    }
}
