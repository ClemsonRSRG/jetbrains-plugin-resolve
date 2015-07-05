package edu.clemson.resolve.plugin.parsing;

import com.google.common.base.Strings;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunRESOLVEOnModuleFile extends Task.Modal {

    public RunRESOLVEOnModuleFile(Project project,
                                  String title, boolean canBeCancelled) {
        super(project, title, canBeCancelled);
    }

    @Override public void run(ProgressIndicator progressIndicator) {
    }

    public static List<String> getRESOLVEArgsAsList(
            Project project, VirtualFile vfile) {
        Map<String,String> argMap = getANTLRArgs(project, vfile);
        List<String> args = new ArrayList<>();
        for (String option : argMap.keySet()) {
            args.add(option);
            String value = argMap.get(option);
            if ( value.length()!=0 ) {
                args.add(value);
            }
        }
        return args;
    }

    public static Map<String,String> getANTLRArgs(Project project, VirtualFile vfile) {
        Map<String,String> args = new HashMap<String, String>();
        args.put("T.resolve", "");
        args.put("-o", "/Users/daniel/Documents/resolve-intellij-plugin/gen");
        args.put("-lib", "-lib /Users/daniel/Documents/resolve-intellij-plugin/src/");
        args.put("-noStdUses", "");

        return args;
    }
}
