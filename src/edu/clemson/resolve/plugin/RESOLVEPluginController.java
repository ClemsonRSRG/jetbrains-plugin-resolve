package edu.clemson.resolve.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class RESOLVEPluginController implements ProjectComponent {

    public static final Logger LOG = Logger.getInstance("RESOLVE RESOLVEPluginController");
    public Project project;

    public RESOLVEPluginController(Project project) {
        this.project = project;
    }

    public static RESOLVEPluginController getInstance(Project project) {
        return project.getComponent(RESOLVEPluginController.class);
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "resolve.ProjectComponent";
    }
}
