package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by danielwelch on 8/20/16.
 */
public class BackgroundableCompile extends Task.Backgroundable {

    public BackgroundableCompile(@Nullable Project project, @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title) {
        super(project, title);
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {

    }
}
