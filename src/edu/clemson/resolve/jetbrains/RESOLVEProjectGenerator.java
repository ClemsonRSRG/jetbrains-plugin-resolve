package edu.clemson.resolve.jetbrains;

import com.intellij.facet.ui.ValidationResult;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGenerator;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEProjectGenerator implements DirectoryProjectGenerator {

    @Nls
    @NotNull
    @Override
    public String getName() {
        return RESOLVEConstants.RESOLVE;
    }

    @Nullable
    @Override
    public Object showGenerationSettings(VirtualFile baseDir) throws ProcessCanceledException {
        return null;
    }

    @Nullable
    @Override
    public Icon getLogo() {
        return RESOLVEIcons.TOOL_ICON;
    }

    @Override
    public void generateProject(@NotNull Project project,
                                @NotNull VirtualFile baseDir,
                                @Nullable Object settings,
                                @NotNull Module module) {

    }

    @NotNull
    @Override
    public ValidationResult validate(@NotNull String baseDirPath) {
        return ValidationResult.OK;
    }
}
