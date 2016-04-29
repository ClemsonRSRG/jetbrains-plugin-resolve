package edu.clemson.resolve.jetbrains.inspections;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.RESOLVEModuleType;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

//note that this code is lifted (almost) verbatim from the go intellij idea plugin here:
//https://github.com/go-lang-plugin-org/go-lang-idea-plugin
public class WrongModuleTypeNotificationProvider
        extends
        EditorNotifications.Provider<EditorNotificationPanel> implements DumbAware {

    private static final Key<EditorNotificationPanel> KEY = Key.create("Wrong module type");
    private static final String DONT_ASK_TO_CHANGE_MODULE_TYPE_KEY = "do.not.ask.to.change.module.type";

    private final Project project;

    public WrongModuleTypeNotificationProvider(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public Key<EditorNotificationPanel> getKey() {
        return KEY;
    }

    @Override
    public EditorNotificationPanel createNotificationPanel(@NotNull VirtualFile file, @NotNull FileEditor fileEditor) {
        if (file.getFileType() != RESOLVEFileType.INSTANCE) return null;
        Module module = ModuleUtilCore.findModuleForFile(file, project);
        return module == null ||
                RESOLVESdkService.getInstance(project).isRESOLVEModule(module) ||
                getIgnoredModules(project).contains(module.getName()) ? null : createPanel(project, module);
    }

    @NotNull
    private static EditorNotificationPanel createPanel(@NotNull final Project project,
                                                       @NotNull final Module module) {
        EditorNotificationPanel panel = new EditorNotificationPanel();
        panel.setText("'" + module.getName() + "' is not a RESOLVE Module, " +
                "therefore some code insight might not work here");
        panel.createActionLabel("Change module type to RESOLVE and reload project", new Runnable() {
            @Override
            public void run() {
                int message = Messages.showOkCancelDialog(project,
                        "Updating module type requires project reload. " +
                                "Proceed?", "Update Module Type",
                        "Reload project", "Cancel", null);
                if (message == Messages.YES) {
                    module.setOption(Module.ELEMENT_TYPE,
                            RESOLVEModuleType.getInstance().getId());
                    project.save();
                    EditorNotifications.getInstance(project)
                            .updateAllNotifications();
                    ProjectManager.getInstance().reloadProject(project);
                }
            }
        });
        panel.createActionLabel("Don't show again for this module", new Runnable() {
            @Override
            public void run() {
                Set<String> ignoredModules = getIgnoredModules(project);
                ignoredModules.add(module.getName());
                PropertiesComponent.getInstance(project).setValue(DONT_ASK_TO_CHANGE_MODULE_TYPE_KEY,
                        StringUtil.join(ignoredModules, ","));
                EditorNotifications.getInstance(project).updateAllNotifications();
            }
        });
        return panel;
    }

    @NotNull
    private static Set<String> getIgnoredModules(@NotNull Project project) {
        String value = PropertiesComponent.getInstance(project).getValue(DONT_ASK_TO_CHANGE_MODULE_TYPE_KEY, "");
        return ContainerUtil.newLinkedHashSet(StringUtil.split(value, ","));
    }
}