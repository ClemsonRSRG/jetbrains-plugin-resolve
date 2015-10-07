package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.text.VersionComparatorUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

public class RESOLVESdkUtil {

    @Nullable public static VirtualFile suggestSdkDirectory() {
        if (SystemInfo.isWindows) {
            throw new UnsupportedOperationException("windows currently not " +
                    "supported as of plugin release v1.0.0");
        }
        if (SystemInfo.isMac || SystemInfo.isLinux) {
            return LocalFileSystem.getInstance()
                    .findFileByPath("/usr/local/resolve");
        }
        return null;
    }

    public static int compareVersions(@NotNull String lhs,
                                      @NotNull String rhs) {
        return VersionComparatorUtil.compare(lhs, rhs);
    }

    @Nullable public static VirtualFile getSdkSrcDir(
            @NotNull PsiElement context) {
        Module module = ModuleUtilCore.findModuleForPsiElement(context);
        VirtualFile sdkSrcDir = getSdkSrcDir(context.getProject(), module);
        return sdkSrcDir != null ? sdkSrcDir : null; //TODO TODO: Shouldn't return null here. 'Guess' it instead
    }

    @Nullable private static VirtualFile getSdkSrcDir(@NotNull Project project,
                                                      @Nullable Module module) {
        String sdkHomePath = RESOLVESdkService.getInstance(project)
                .getSdkHomePath(module);
        String sdkVersionString = RESOLVESdkService.getInstance(project)
                .getSdkVersion(module);
        VirtualFile sdkSrcDir = null;
        if (sdkHomePath != null && sdkVersionString != null) {
            File sdkSrcDirFile =
                    new File(sdkHomePath, getSrcLocation(sdkVersionString));
            sdkSrcDir = LocalFileSystem
                    .getInstance().findFileByIoFile(sdkSrcDirFile);
        }
        return sdkSrcDir;
    }

    @NotNull public static Collection<VirtualFile> getSdkDirectoriesToAttach(
            @NotNull String sdkPath, @NotNull String versionString) {
        String srcPath = getSrcLocation(versionString);
        VirtualFile src = VirtualFileManager.getInstance().findFileByUrl(
                VfsUtilCore.pathToUrl(FileUtil.join(sdkPath, srcPath)));
        if (src != null && src.isDirectory()) {
            return Collections.singletonList(src);
        }
        return Collections.emptyList();
    }

    @Nullable public static String retrieveRESOLVEVersion(
            @NotNull final String sdkPath) {
        String curJarName = RESOLVESdkService.getRESOLVEToolJarPath(sdkPath);
        return cutoutVersion(curJarName);
    }


    /**
     * This includes a version parameter if someone down the road decides to
     * change structure or names of our core library's "src" directory. This
     * way we can compare the version string and return the appropriate location,
     * preserving compatibility with older versions.
     */
    @NotNull static String getSrcLocation(@NotNull String version) {
        return "src";
    }

    public static String cutoutVersion(String jarName) {
        return jarName.substring(jarName.indexOf('-',0)+1,
                jarName.indexOf('-',0)+6);
    }

    @NotNull public static Collection<Module> getRESOLVEModules(
            @NotNull Project project) {
        if (project.isDefault()) return Collections.emptyList();
        final RESOLVESdkService sdkService =
                RESOLVESdkService.getInstance(project);
        return ContainerUtil.filter(ModuleManager.getInstance(project)
                .getModules(), new Condition<Module>() {
            @Override
            public boolean value(Module module) {
                return sdkService.isRESOLVEModule(module);
            }
        });
    }

    @Nullable @Contract("null -> null") public static String getImportPath(
            @Nullable final PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            return null;
        }
        return CachedValuesManager.getCachedValue(psiDirectory, new CachedValueProvider<String>() {
            @Nullable
            @Override
            public Result<String> compute() {
                Project project = psiDirectory.getProject();
                Module module = ModuleUtilCore.findModuleForPsiElement(psiDirectory);
                String path = getPathRelativeToSdkAndLibraries(psiDirectory.getVirtualFile(), project, module);
                return Result.create(path, getSdkAndLibrariesCacheDependencies(psiDirectory));
            }
        });
    }

    @Nullable public static String getPathRelativeToSdkAndLibraries(
            @NotNull VirtualFile file, @Nullable Project project,
            @Nullable Module module) {
        if (project != null) {
            Collection<VirtualFile> roots = newLinkedHashSet();
            ContainerUtil.addIfNotNull(roots, getSdkSrcDir(project, module));
            roots.addAll(getGoPathSources(project, module));

            String result = null;
            for (VirtualFile root : roots) {
                String relativePath = VfsUtilCore.getRelativePath(file, root, '/');
                if (StringUtil.isNotEmpty(relativePath) && (result == null || result.length() > relativePath.length())) {
                    result = relativePath;
                }
            }
            if (result != null) return result;
        }

        String filePath = file.getPath();
        int src = filePath.lastIndexOf("/src/");
        if (src > -1) {
            return filePath.substring(src + 5);
        }
        return null;
    }

}
