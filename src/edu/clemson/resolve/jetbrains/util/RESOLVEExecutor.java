package edu.clemson.resolve.jetbrains.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.util.ThreeState;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEExecutor {

    private static final Logger LOGGER = Logger.getInstance(RESOLVEExecutor.class);

    @NotNull private final Project project;
    @Nullable private final Module module;
    @Nullable private String resolveRoot;
    @Nullable private String resolvePath;
    @Nullable private String envPath;
    @Nullable private String workingDirectory;
    @Nullable private String presentableName;

    private boolean showRESOLVEEnvVariables = true;

    private RESOLVEExecutor(@NotNull Project project, @Nullable Module module) {
        this.project = project;
        this.module = module;
    }

    /*public static RESOLVEExecutor in(@NotNull Project project, @Nullable Module module) {
        return module != null ? in(module) : in(project);
    }*/

    @NotNull
    private static RESOLVEExecutor in(@NotNull Project project) {
        return new RESOLVEExecutor(project, null)
                .withRESOLVERoot(RESOLVESdkService.getInstance(project).getSdkHomePath(null))
                .withRESOLVEPath(RESOLVESdkUtil.retrieveRESOLVEPath(project, null));
                //.withRESOLVEPath(RESOLVESdkUtil.retrieveEnvironmentPathForRESOLVE(project, null));
    }

    @NotNull
    public RESOLVEExecutor withPresentableName(@Nullable String presentableName) {
        this.presentableName = presentableName;
        return this;
    }

    @NotNull
    public RESOLVEExecutor withRESOLVERoot(@Nullable String resolveRoot) {
        this.resolveRoot = resolveRoot;
        return this;
    }

    @NotNull
    public RESOLVEExecutor withRESOLVEPath(@Nullable String resolvePath) {
        this.resolvePath = resolvePath;
        return this;
    }


}
