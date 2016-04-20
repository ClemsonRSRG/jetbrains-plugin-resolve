/*
 * Copyright 2013-2016 Sergey Ignatov, Alexander Zolotov, Florin Patan
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

package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathMacros;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.util.EnvironmentUtil;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class ResEnvironmentRESOLVEPathModificationTracker {

    private final Set<String> pathsToTrack = ContainerUtil.newHashSet();
    private final Collection<VirtualFile> resolvePathRoots =
            ContainerUtil.newLinkedHashSet();

    public ResEnvironmentRESOLVEPathModificationTracker() {
        String resolvePath = retrieveGoPathFromEnvironment();
        if (resolvePath != null) {
            String home = SystemProperties.getUserHome();
            for (String s : StringUtil.split(resolvePath, File.pathSeparator)) {
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

        VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileAdapter() {
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

    private synchronized void updateRESOLVEPathRoots(
            Collection<VirtualFile> newRoots) {
        resolvePathRoots.clear();
        resolvePathRoots.addAll(newRoots);
    }

    private synchronized Collection<VirtualFile> getRESOLVEPathRoots() {
        return resolvePathRoots;
    }

    public static Collection<VirtualFile> getRESOLVEEnvironmentRESOLVEPathRoots() {
        return ServiceManager.getService(
                ResEnvironmentRESOLVEPathModificationTracker.class).getRESOLVEPathRoots();
    }

    @Nullable
    public static String retrieveGoPathFromEnvironment() {
        if (ApplicationManager.getApplication().isUnitTestMode()) return null;

        String path = EnvironmentUtil.getValue(RESOLVEConstants.RESOLVE_PATH);
        return path != null ? path : PathMacros.getInstance()
                .getValue(RESOLVEConstants.RESOLVE_PATH);
    }
}
