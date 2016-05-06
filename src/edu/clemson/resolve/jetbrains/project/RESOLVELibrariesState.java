package edu.clemson.resolve.jetbrains.project;

import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

class RESOLVELibrariesState {
    @NotNull
    private Collection<String> urls = ContainerUtil.newArrayList();

    @NotNull
    Collection<String> getUrls() {
        return urls;
    }

    void setUrls(@NotNull Collection<String> urls) {
        this.urls = urls;
    }
}
