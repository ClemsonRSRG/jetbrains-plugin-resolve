package edu.clemson.resolve.jetbrains.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEExecutor {

    @NotNull private final Project project;
    @Nullable private final Module module;
    @Nullable private String resolveRoot;
    @Nullable private String resolvePath;
    @Nullable private String envPath;
    @Nullable private String workingDirectory;
    private boolean showRESOLVEEnvVariables = true;

    private RESOLVEExecutor(@NotNull Project project, @Nullable Module module) {
        this.project = project;
        this.module = module;
    }
}
