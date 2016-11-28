package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.RESOLVECompiler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Probably going to replace external annotator.. Simplicity is key. Also its nice to still be able to type
//things that the compiler might not currently accept without having a bunch of errors pop up.
//Remember: This isn't a commercial product. It's very much still a research effort.
public class AnalyzeAction extends RESOLVEAction {
    public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");

    @Override
    public void update(AnActionEvent e) {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        if (resolveFile == null) return;
        if (e.getProject() == null) return;

        String grammarFileName = resolveFile.getPath();
        LOG.info("doAnnotate " + grammarFileName);
        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(e.getProject(), resolveFile).getPath());
        List<String> args = RunRESOLVEOnLanguageFile.getRESOLVEArgsAsList(argMap);
        String fileName = resolveFile.getName();
        args.add(0, fileName);
        final RESOLVECompiler resolve = new RESOLVECompiler(args.toArray(new String[args.size()]));
        
    }
}
