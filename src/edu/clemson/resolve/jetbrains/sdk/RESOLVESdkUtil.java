package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

public class RESOLVESdkUtil {
    private static final Pattern RESOLVE_VERSION_PATTERN = Pattern.compile("resolve-([\\d.]+\\w+(\\d+)?)-complete");
    private static final Key<String> RESOLVE_VERSION_DATA_KEY = Key.create("RESOLVE_VERSION_KEY");

    private RESOLVESdkUtil() {
    }

    @Nullable
    public static VirtualFile getSdkSrcDir(
            @NotNull final Project project, @Nullable final Module module) {
        if (module != null) {
            return CachedValuesManager.getManager(project).getCachedValue(module,
                    new CachedValueProvider<VirtualFile>() {
                        @Nullable
                        @Override
                        public Result<VirtualFile> compute() {
                            RESOLVESdkService sdkService = RESOLVESdkService.getInstance(module.getProject());
                            return Result.create(getInnerSdkSrcDir(sdkService, module), sdkService);
                        }
                    });
        }
        return CachedValuesManager.getManager(project).getCachedValue(project,
                new CachedValueProvider<VirtualFile>() {
                    @Nullable
                    @Override
                    public Result<VirtualFile> compute() {
                        RESOLVESdkService sdkService = RESOLVESdkService.getInstance(project);
                        return Result.create(getInnerSdkSrcDir(sdkService, null), sdkService);
                    }
                });
    }

    @Nullable
    private static VirtualFile getSdkSrcDir(@NotNull String sdkPath,
                                            @NotNull String sdkVersion) {
        String srcPath = "src";
        VirtualFile file = VirtualFileManager.getInstance()
                .findFileByUrl(VfsUtilCore.pathToUrl(
                        FileUtil.join(sdkPath, srcPath)));
        return file != null && file.isDirectory() ? file : null;
    }

    @Nullable
    private static VirtualFile getInnerSdkSrcDir(
            @NotNull RESOLVESdkService sdkService, @Nullable Module module) {
        String sdkHomePath = sdkService.getSdkHomePath(module);
        return sdkHomePath != null ? getSdkSrcDir(sdkHomePath) : null;
    }

    @Nullable
    private static VirtualFile getSdkSrcDir(@NotNull String sdkPath) {
        String srcPath = "src";
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(
                VfsUtilCore.pathToUrl(FileUtil.join(sdkPath, srcPath)));
        return file != null && file.isDirectory() ? file : null;
    }

    @NotNull
    public static LinkedHashSet<VirtualFile> getSourcesPathsToLookup(
            @NotNull Project project, @Nullable Module module) {
        LinkedHashSet<VirtualFile> sdkSrcs = newLinkedHashSet();
        ContainerUtil.addIfNotNull(sdkSrcs, getSdkSrcDir(project, module));
        return sdkSrcs;
    }

    @Nullable
    public static VirtualFile suggestSdkDirectory() {
        if (SystemInfo.isWindows) {
            return LocalFileSystem.getInstance().findFileByPath("C:\\resolve");
        }
        if (SystemInfo.isMac || SystemInfo.isLinux) {
            VirtualFile usrLocal = LocalFileSystem.getInstance()
                    .findFileByPath("/usr/local/resolve");
            if (usrLocal != null) return usrLocal;
        }
        return null;
    }

    @Nullable
    public static String retrieveRESOLVEVersion(@NotNull String sdkPath) {
        VirtualFile sdkRoot = VirtualFileManager.getInstance()
                .findFileByUrl(VfsUtilCore.pathToUrl(sdkPath));
        String version = null;
        if (sdkRoot != null) {
            String cachedVersion = sdkRoot.getUserData(RESOLVE_VERSION_DATA_KEY);
            if (cachedVersion != null) {
                return !cachedVersion.isEmpty() ? cachedVersion : null;
            }
            VirtualFile compilerCandidate = null;
            for (VirtualFile f : sdkRoot.getChildren()) {
                if (f.getName().endsWith(".jar")) {
                    compilerCandidate = f;
                    break;
                }
            }
            if (compilerCandidate == null) {
                version = null;
                RESOLVESdkService.LOG.debug("Cannot find compiler jar in resolve sdk home directory");
            } else {
                String fileName = compilerCandidate.getName();
                version = parseRESOLVEVersion(fileName);
                if (version == null) {
                    RESOLVESdkService.LOG.debug("Cannot retrieve go version from compiler jar name: " + fileName);
                }
                sdkRoot.putUserData(RESOLVE_VERSION_DATA_KEY, StringUtil.notNullize(version));
            }
            return version;
        }
        return null;
    }

    @NotNull
    public static Collection<VirtualFile> getSdkDirectoriesToAttach(
            @NotNull String sdkPath, @NotNull String versionString) {
        // scr is enough at the moment, possible process binaries from pkg
        return ContainerUtil.createMaybeSingletonList(
                getSdkSrcDir(sdkPath, versionString));
    }

    @Nullable
    public static String parseRESOLVEVersion(@NotNull String text) {
        Matcher matcher = RESOLVE_VERSION_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
