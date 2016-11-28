package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.impl.DocumentMarkupModel;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtil;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.AnnotatedModule;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.annotation.AnnotatorCompilerListener;
import edu.clemson.resolve.jetbrains.annotation.RESOLVEExternalAnnotator;

import java.util.*;

//Probably going to replace external annotator.. Simplicity is key. Also its nice to still be able to type
//things that the compiler might not currently accept without having a bunch of errors pop up.
//Remember: This isn't a commercial product. It's very much still a research effort.
public class AnalyzeAction extends RESOLVEAction {
    public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(RESOLVEIcons.CHECKMARK);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        if (resolveFile == null) return;
        if (e.getProject() == null) return;
        PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        if (file == null) return;

         new ArrayList<>();
        RESOLVEExternalAnnotator a = new RESOLVEExternalAnnotator();
        a.collectInformation(file);
        List<RESOLVEExternalAnnotator.Issue> issues = a.doAnnotate(file);

        int i;
        i =0;
        /*String grammarFileName = resolveFile.getPath();
        LOG.info("doAnnotate " + grammarFileName);
        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(e.getProject(), resolveFile).getPath());
        List<String> args = RunRESOLVEOnLanguageFile.getRESOLVEArgsAsList(argMap);
        String fileName = resolveFile.getName();
        args.add(0, fileName);
        final RESOLVECompiler resolve = new RESOLVECompiler(args.toArray(new String[args.size()]));

        resolve.removeListeners();
        AnnotatorCompilerListener listener = new AnnotatorCompilerListener();
        resolve.addListener(listener);
        resolve.processCommandLineTargets();*/



    }
}
