package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.externalSystem.action.OpenTasksActivationManagerAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.*;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.AnnotatedModule;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.RESOLVEPluginController;
import edu.clemson.resolve.jetbrains.annotation.AnnotatorCompilerListener;
import edu.clemson.resolve.jetbrains.annotation.RESOLVEExternalAnnotator;
import edu.clemson.resolve.jetbrains.verifier.VerifierPanel;
import edu.clemson.resolve.proving.Metrics;
import edu.clemson.resolve.proving.PerVCProverModel;
import edu.clemson.resolve.proving.ProverListener;
import edu.clemson.resolve.vcgen.VC;
import edu.clemson.resolve.vcgen.model.VCOutputFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

public class ProveAction extends RESOLVEAction {
    private boolean proved = false;
    private static DataKey<Boolean> key = DataKey.create("proved");

   /* @Override
    public void update(AnActionEvent e) {
        if (proved) {
            e.getPresentation().setIcon(RESOLVEIcons.CONCEPT_EXT);
        }
        else {
            e.getPresentation().setIcon(RESOLVEIcons.PRECIS);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        /*int i = 0;
        while (i != 5000000) {
            i++;
        }
        proved = true;
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOGGER.error("actionPerformed (genVCs): no project for " + e);
            return; // whoa!
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOGGER.info("generate VCs actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE VC Generation";
        boolean canBeCancelled = true;

        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        boolean forceGeneration = true; // from action, they really mean it
        Map<String, String> argMap = new LinkedHashMap<>();
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Description") {
            @Override
            public void run(@NotNull final ProgressIndicator progressIndicator) {


                // put your code here
                // to access Project use myProject field
            }
        });
    }*/

    private static final Logger LOGGER = Logger.getInstance("RESOLVEGenerateVCsAction");

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
            LOGGER.error("actionPerformed (genVCs): no project for " + e);
            return; // whoa!
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOGGER.info("prove actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE Prove";
        boolean canBeCancelled = true;

        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        boolean forceGeneration = true; // from action, they really mean it
        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        title);


        boolean successful = false;
        List<String> args = new ArrayList<String>();
        args.add(resolveFile.getPath());
        args.add("-lib");
        args.add(RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());
        args.add("-prove");
        RESOLVECompiler compiler = new RESOLVECompiler(args.toArray(new String[args.size()]));
        MyProverListener pl = new MyProverListener();
        compiler.addProverListener(pl);

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Description") {
            @Override
            public void run(@NotNull final ProgressIndicator progressIndicator) {
                compiler.processCommandLineTargets();
                // put your code here
                // to access Project use myProject field
            }
        });
        int i = 0;

        while (i < 200) {
            System.out.println("number of proofs recieved: " + pl.vcIsProved.size());
            i++;
        }
       /*
        if (successful && !editor.isDisposed()) {
            MarkupModel markup = editor.getMarkupModel();
            processResult(gen.getVCOutput());

            RESOLVEPluginController controller = RESOLVEPluginController.getInstance(project);

            //TODO, if we do runProverAction(), instead of passing 'null' for this Runnable object that's
            //expected, we'll pass a process for proving the vcs. For now though, since we're just showing
            //look at the logika plugin (in the lower right hand corner of the ide)
            //and see how they do that processing
            markup.removeAllHighlighters();

            VCOutputFile vcOutput = gen.getVCOutput();

            //A mapping from [line number] -> [vc_1, .., vc_j]
            Map<Integer, List<VC>> byLine = vcOutput.getVCsGroupedByLineNumber();
            List<RangeHighlighter> vcRelatedHighlighters = new ArrayList<>();

            for (Map.Entry<Integer, List<VC>> vcsByLine : vcOutput.getVCsGroupedByLineNumber().entrySet()) {
                List<AnAction> actionsPerVC = new ArrayList<>();
                //create clickable actions for each vc
                for (VC vc : vcsByLine.getValue()) {
                    actionsPerVC.add(new AnAction("VC " + vc.getNumber()+ ": " + vc.getExplanation()) {

                        @Override
                        public void update(AnActionEvent e) {

                        }

                        @Override
                        public void actionPerformed(AnActionEvent e) {
                            controller.getVerifierWindow().show(null);  //open the tool(verifier)window
                            VerifierPanel verifierPanel = controller.getVerifierPanel();

                            //I performed an action, let's remember to clean up after ourselves before we go and update
                            //the vc panel
                            verifierPanel.setActiveVcPanel(new VerifierPanel.VCPanelMock(project, vc));
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
                        DefaultActionGroup g = new DefaultActionGroup();
                        g.addAll(actionsPerVC);
                        return g;
                    }

                    @Nullable
                    public AnAction getClickAction() {
                        return null;
                    }

                });
                vcRelatedHighlighters.add(highlighter);
                //actionsPerVC.clear();
            }

            editor.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void beforeDocumentChange(DocumentEvent event) {
                }
                @Override
                public void documentChanged(DocumentEvent event) {
                    //remove all highlighters
                    for (RangeHighlighter h : vcRelatedHighlighters) {
                        markup.removeHighlighter(h);
                    }
                    VerifierPanel verifierPanel = controller.getVerifierPanel();
                    controller.getVerifierWindow().hide(null);
                    verifierPanel.revertToBaseGUI();
                }
            });
        }*/
    }
    public static class MyProverListener implements ProverListener {
        public final Map<String, Boolean> vcIsProved = new com.intellij.util.containers.HashMap<>();
        @Override
        public void progressUpdate(double v) {
        }

        @Override
        public void vcResult(boolean b, PerVCProverModel perVCProverModel, Metrics metrics) {
            vcIsProved.put(perVCProverModel.getVCName(), b);
        }
    }
    private void processResult(VCOutputFile vcs) {

    }
}