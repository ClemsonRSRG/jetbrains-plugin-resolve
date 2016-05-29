package edu.clemson.resolve.jetbrains.actions;

import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.tools.SimpleActionGroup;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.RESOLVEPluginController;
import edu.clemson.resolve.jetbrains.verifier.VerifierPanel;
import edu.clemson.resolve.vcgen.VC;
import edu.clemson.resolve.vcgen.model.VCOutputFile;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

public class GenerateVCsAction extends RESOLVEAction {

    private static final Logger LOG = Logger.getInstance("RESOLVEGenerateVCsAction");

    @Override
    public void update(AnActionEvent e) {
        super.update(e); //checks we're dealing with a resolve file (and that's it)

    }
    //IDEAS:
    // o Have a place to view derivations in a cool way.. (maybe have completion for these fragments of assertive code,
    //          navigational features?, etc
    // o Have a meta-language for crafting assertive code blocks and reduce them in real time showing the steps
    // o Have where users can browse/peruse the rules? hmm.
    // o Here's another interesting idea:
    //      make VCGeneration parameterizable (configurable!)
    //          --have option for generating parsimonious vcs
    //          --have option for generating non-parsimonious vcs
    // o Show HOW rules are applied -- and what happens when the parsimonious step tosses out gives. You can use one of the
    //      interesting set visualization techniques discussed in 804 (consult Levine about this)

    //for now though, lets just try to do what the web interface does..

    //classes of interest:
    //TextAttributes
    //MarkupModel <-- probably the most likely candidate for a place to start.
    //LineMarkerProvider

    //PluginController (antlr v4 + Psi viewer -- this one could be useful since clicking the psi node in the editor
    //manipulates the PsiViewer panel..
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOG.error("actionPerformed (genVCs): no project for " + e);
            return; // whoa!
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOG.info("generate VCs actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE VC Generation";
        boolean canBeCancelled = true;

        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void beforeDocumentChange(DocumentEvent event) {

            }
            @Override
            public void documentChanged(DocumentEvent event) {
               // editor.getMarkupModel().
            }
        });
        boolean forceGeneration = true; // from action, they really mean it
        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        title);

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());
        argMap.put("-vcs", "");
        gen.addArgs(argMap);
        ProgressManager.getInstance().run(gen); //, "Generating", canBeCancelled, e.getData(PlatformDataKeys.PROJECT));

        if (!editor.isDisposed()) {
            MarkupModel markup = editor.getMarkupModel();
            processResult(gen.getVCOutput());

            RESOLVEPluginController controller = RESOLVEPluginController.getInstance(project);

            //TODO, if we do runProverAction(), instead of passing 'null' for this Runnable object that's
            //expected, we'll pass a process for proving the vcs. For now though, since we're just showing
            //look at the logika plugin (in the lower right hand corner of the ide)
            //and see how they do that processing
            markup.removeAllHighlighters();

            VCOutputFile vcOutput = gen.getVCOutput();
            Map<Integer, List<VC>> byLine = vcOutput.getVCsGroupedByLineNumber();
            for (Map.Entry<Integer, List<VC>> vcsByLine : vcOutput.getVCsGroupedByLineNumber().entrySet()) {
                List<AnAction> actionsPerVC = new ArrayList<>();
                //create clickable actions for each vc
                for (VC vc : vcsByLine.getValue()) {
                    actionsPerVC.add(new AnAction("VC " + vc.getName() + ": " + vc.getConsequentInfo().explanation) {
                        @Override
                        public void actionPerformed(AnActionEvent e) {
                            controller.getVerifierWindow().show(null);
                            VerifierPanel verifierPanel = controller.getVerifierPanel();
                        }

                    });
                }
                RangeHighlighter highlighter =
                        markup.addLineHighlighter(vcsByLine.getKey() - 1, HighlighterLayer.ELEMENT_UNDER_CARET, null);
                highlighter.setGutterIconRenderer(new GutterIconRenderer() {
                    @NotNull
                    @Override
                    public Icon getIcon() {
                        return RESOLVEIcons.VC;
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }

                    @Override
                    public int hashCode() {
                        return 0;
                    }

                    @Override
                    public boolean isNavigateAction() {
                        return true;
                    }

                    @Nullable
                    public ActionGroup getPopupMenuActions() {
                        //TODO: Find a nicer looking actiongroup to use.
                        DefaultActionGroup g = new DefaultActionGroup();
                        g.addAll(actionsPerVC);
                        return g;
                    }

                    @Nullable
                    public AnAction getClickAction() {
                        return null;
                    }

                });
                //actionsPerVC.clear();
            }
        }
    }

    private void processResult(VCOutputFile vcs) {

    }

}
