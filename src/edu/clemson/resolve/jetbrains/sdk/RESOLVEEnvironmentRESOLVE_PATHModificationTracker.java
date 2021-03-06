package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.project.RESOLVEEnvUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class RESOLVEEnvironmentRESOLVE_PATHModificationTracker {

    private final Set<String> pathsToTrack = ContainerUtil.newHashSet();

    private final Collection<VirtualFile> resolvePathRoots = ContainerUtil.newLinkedHashSet();

    public RESOLVEEnvironmentRESOLVE_PATHModificationTracker() {
        String resPath = RESOLVEEnvUtil.retrieveRESOLVEPathFromEnvironment();
        if (resPath != null) {
            String home = SystemProperties.getUserHome();
            for (String s : StringUtil.split(resPath, File.pathSeparator)) {
                if (s.contains("$HOME")) {
                    if (home == null) {
                        continue;
                    }
                    s = s.replaceAll("\\$HOME", home);
                }
                pathsToTrack.add(s);
            }
        }
        recalculateFiles();

        VirtualFileManager.getInstance().addVirtualFileListener(
                new VirtualFileAdapter() {
                    @Override
                    public void fileCreated(@NotNull VirtualFileEvent event) {
                        handleEvent(event);
                    }

                    @Override
                    public void fileDeleted(@NotNull VirtualFileEvent event) {
                        handleEvent(event);
                    }

                    @Override
                    public void fileMoved(@NotNull VirtualFileMoveEvent event) {
                        handleEvent(event);
                    }

                    @Override
                    public void fileCopied(@NotNull VirtualFileCopyEvent event) {
                        handleEvent(event);
                    }

                    private void handleEvent(VirtualFileEvent event) {
                        if (pathsToTrack.contains(event.getFile().getPath())) {
                            recalculateFiles();
                        }
                    }
                });
    }

    private void recalculateFiles() {
        Collection<VirtualFile> result = ContainerUtil.newLinkedHashSet();
        for (String path : pathsToTrack) {
            ContainerUtil.addIfNotNull(result, LocalFileSystem.getInstance()
                    .findFileByPath(path));
        }
        updateRESOLVEPathRoots(result);
    }

    private synchronized void updateRESOLVEPathRoots(Collection<VirtualFile> newRoots) {
        resolvePathRoots.clear();
        resolvePathRoots.addAll(newRoots);
    }

    private synchronized Collection<VirtualFile> getRESOLVEPathRoots() {
        return resolvePathRoots;
    }

    public static Collection<VirtualFile> getRESOLVEEnvironmentRESOLVE_PATHRoots() {
        return ServiceManager.getService(RESOLVEEnvironmentRESOLVE_PATHModificationTracker.class)
                .getRESOLVEPathRoots();
    }
}
