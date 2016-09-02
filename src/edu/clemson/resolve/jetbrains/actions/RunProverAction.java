package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RunProverAction extends AnAction {

    public RunProverAction(String tooltip, String description, Icon icon, ProveAction.MyProverListener listener) {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Proving") {
            @Override
            public void run(@NotNull final ProgressIndicator progressIndicator) {
                compiler.processCommandLineTargets();
            }
        });


        //List<SidebarSection> proved = new ArrayList<>();
        //List<SidebarSection> notProved = new ArrayList<>();

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Updating Presentation") {
            @Override
            public void run(@NotNull final ProgressIndicator progressIndicator) {
                Map<String, Boolean> processed = new HashMap<String, Boolean>();
                for (VC vc : vco.getFinalVCs()) {
                    processed.put(vc.getName(), false);
                }
                while (pl.vcIsProved.size() != vco.getFinalVCs().size()) {
                    for (VC vc : vco.getFinalVCs()) {
                        if (pl.vcIsProved.containsKey(vc.getName()) && !processed.get(vc.getName())) {
                            processed.put(vc.getName(), true);
                            ConditionCollapsiblePanel section = verifierPanel.vcSelectorPanel.vcTabs.get(vc.getNumber());
                            section.changeToFinalState(pl.vcIsProved.get(vc.getName()) ?
                                    ConditionCollapsiblePanel.State.PROVED :
                                    ConditionCollapsiblePanel.State.NOT_PROVED);
                        }
                    }
                }
            }
        });*/
    }
}
