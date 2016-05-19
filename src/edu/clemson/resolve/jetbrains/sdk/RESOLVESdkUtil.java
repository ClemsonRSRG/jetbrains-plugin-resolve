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
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.project.RESOLVEApplicationLibrariesService;
import edu.clemson.resolve.jetbrains.project.RESOLVELibrariesService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

public class RESOLVESdkUtil {
    private static final Pattern RESOLVE_VERSION_PATTERN = Pattern.compile("resolve-([\\d.]+\\w+(\\d+)?)-complete");
    private static final Key<String> RESOLVE_VERSION_DATA_KEY = Key.create("RESOLVE_VERSION_KEY");

    private RESOLVESdkUtil() {
    }

    @Nullable
    public static VirtualFile getSdkSrcDir(@NotNull final Project project, @Nullable final Module module) {
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

    @NotNull
    public static LinkedHashSet<VirtualFile> getSourcesPathsToLookup(@NotNull Project project,
                                                                     @Nullable Module module) {
        LinkedHashSet<VirtualFile> sdkAndPathSrcs = newLinkedHashSet();
        ContainerUtil.addIfNotNull(sdkAndPathSrcs, getSdkSrcDir(project, module));

        Collection<VirtualFile> pathSrcs = getRESOLVEPathSources(project, module);
        ContainerUtil.addAllNotNull(sdkAndPathSrcs, getRESOLVEPathSources(project, module));
        return sdkAndPathSrcs;
    }

    @NotNull
    public static Collection<VirtualFile> getRESOLVEPathSources(@NotNull final Project project,
                                                                @Nullable final Module module) {
        if (module != null) {
            return CachedValuesManager.getManager(project).getCachedValue(
                    module, new CachedValueProvider<Collection<VirtualFile>>() {
                        @Nullable
                        @Override
                        public Result<Collection<VirtualFile>> compute() {
                            Collection<VirtualFile> result = newLinkedHashSet();
                            Project project = module.getProject();
                            RESOLVESdkService sdkService = RESOLVESdkService.getInstance(project);
                            result.addAll(getInnerRESOLVEPathSources(project, module));
                            return Result.create(result, getSdkAndLibrariesCacheDependencies(project, module));
                        }
                    });
        }
        return CachedValuesManager.getManager(project).getCachedValue(project,
                new CachedValueProvider<Collection<VirtualFile>>() {
                    @Nullable
                    @Override
                    public Result<Collection<VirtualFile>> compute() {
                        return Result.create(getInnerRESOLVEPathSources(project, null),
                                getSdkAndLibrariesCacheDependencies(project, null));
                    }
                });
    }

    @NotNull
    private static Collection<Object> getSdkAndLibrariesCacheDependencies(@NotNull Project project,
                                                                          @Nullable Module module) {
        Collection<Object> dependencies = ContainerUtil.newArrayList(
                (Object[]) RESOLVELibrariesService.getModificationTrackers(project, module));
        ContainerUtil.addAllNotNull(dependencies, RESOLVESdkService.getInstance(project));
        return dependencies;
    }

    /**
     * Retrieves root directories from RESOLVEPATH env-variable. This method doesn't consider user defined libraries,
     * for that case use {@link {@link this#getRESOLVEPathRoots(Project, Module)}
     */
    @NotNull
    public static Collection<VirtualFile> getRESOLVEPathsRootsFromEnvironment() {
        return RESOLVEEnvironmentRESOLVE_PATHModificationTracker.getRESOLVEEnvironmentRESOLVE_PATHRoots();
    }

    @NotNull
    private static List<VirtualFile> getInnerRESOLVEPathSources(@NotNull Project project, @Nullable Module module) {
        return ContainerUtil.mapNotNull(getRESOLVEPathRoots(project, module),
                new RetrieveSubDirectoryOrSelfFunction("src"));
    }

    @NotNull
    private static Collection<VirtualFile> getRESOLVEPathRoots(@NotNull Project project, @Nullable Module module) {
        Collection<VirtualFile> roots = ContainerUtil.newArrayList();
        if (RESOLVEApplicationLibrariesService.getInstance().isUsingRESOLVEPathFromSystemEnvironment()) {
            roots.addAll(getRESOLVEPathsRootsFromEnvironment());
        }
        roots.addAll(module != null ?
                RESOLVELibrariesService.getUserDefinedLibraries(module) :
                RESOLVELibrariesService.getUserDefinedLibraries(project));
        return roots;
    }

    @Nullable
    private static VirtualFile getInnerSdkSrcDir(@NotNull RESOLVESdkService sdkService, @Nullable Module module) {
        String sdkHomePath = sdkService.getSdkHomePath(module);
        String sdkVersionString = sdkService.getSdkVersion(module);
        return sdkHomePath != null && sdkVersionString != null ? getSdkSrcDir(sdkHomePath, sdkVersionString) : null;
    }

    @Nullable
    static VirtualFile suggestSdkDirectory() {
        if (SystemInfo.isWindows) {
            return LocalFileSystem.getInstance().findFileByPath("C:\\resolve-lite");
        }
        if (SystemInfo.isMac || SystemInfo.isLinux) {
            VirtualFile usrLocal = LocalFileSystem.getInstance().findFileByPath("/usr/local/resolve-lite");
            if (usrLocal != null) return usrLocal;
        }
        return null;
    }

    @Nullable
    static String retrieveRESOLVEVersion(@NotNull String sdkPath) {
        VirtualFile sdkRoot = VirtualFileManager.getInstance().findFileByUrl(VfsUtilCore.pathToUrl(sdkPath));
        String version = null;
        if (sdkRoot != null) {
            String cachedVersion = sdkRoot.getUserData(RESOLVE_VERSION_DATA_KEY);
            if (cachedVersion != null) {
                return !cachedVersion.isEmpty() ? cachedVersion : null;
            }
            VirtualFile compilerDir = sdkRoot.findFileByRelativePath("compiler");
            if (compilerDir == null || !compilerDir.isDirectory()) {
                RESOLVESdkService.LOG.debug("Cannot find compiler jar in resolve sdk home directory");
                return null;
            }
            VirtualFile compilerCandidate = null;

            for (VirtualFile f : compilerDir.getChildren()) {
                if (f.getName().endsWith(".jar")) {
                    compilerCandidate = f;
                    break;
                }
            }
            if (compilerCandidate == null) {
                version = null;
                RESOLVESdkService.LOG.debug("Cannot find compiler jar in resolve sdk home directory");
            }
            else {
                String fileName = compilerCandidate.getName();
                version = parseRESOLVEVersion(fileName);
                if (version == null) {
                    RESOLVESdkService.LOG.debug("Cannot retrieve RESOLVE version from compiler jar name: " + fileName);
                }
                sdkRoot.putUserData(RESOLVE_VERSION_DATA_KEY, StringUtil.notNullize(version));
            }
            return version;
        }
        return null;
    }

    @NotNull
    static Collection<VirtualFile> getSdkDirectoriesToAttach(@NotNull String sdkPath, @NotNull String versionString) {
        // src is enough at the moment, possible process binaries from pkg
        return ContainerUtil.createMaybeSingletonList(getSdkSrcDir(sdkPath, versionString));
    }

    @Nullable
    static String parseRESOLVEVersion(@NotNull String text) {
        Matcher matcher = RESOLVE_VERSION_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static class RetrieveSubDirectoryOrSelfFunction implements Function<VirtualFile, VirtualFile> {
        @NotNull
        private final String subdirName;

        RetrieveSubDirectoryOrSelfFunction(@NotNull String subdirName) {
            this.subdirName = subdirName;
        }

        @Override
        public VirtualFile fun(VirtualFile file) {
            return file == null || FileUtil.namesEqual(subdirName, file.getName()) ? file : file.findChild(subdirName);
        }
    }
}
