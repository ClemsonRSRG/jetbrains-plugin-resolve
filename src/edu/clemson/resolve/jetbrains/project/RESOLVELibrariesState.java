package edu.clemson.resolve.jetbrains.project;

import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RESOLVELibrariesState {

    @NotNull private Collection<String> urls = ContainerUtil.newArrayList();

    @NotNull public Collection<String> getUrls() {
        return urls;
    }

    public void setUrls(@NotNull Collection<String> urls) {
        this.urls = urls;
    }
}
