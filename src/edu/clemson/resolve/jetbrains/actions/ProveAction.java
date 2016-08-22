package edu.clemson.resolve.jetbrains.actions;

import com.intellij.ide.util.treeView.AbstractTreeBuilder;
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
import com.intellij.openapi.util.Key;
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

    private static final Logger LOGGER = Logger.getInstance("RESOLVEGenerateVCsAction");

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOGGER.error("actionPerformed (genVCs): no project for " + e);
            return;
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOGGER.info("prove actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE Prove";
        boolean canBeCancelled = true;

        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        title);

        MyProverListener pl = new MyProverListener();
        VCOutputFile vco = generateVCs(resolveFile, editor, project);
        //give each action an instance of the prover listener and make Update() print the result as it comes back produce
        if (vco == null) return;
        presentVCs(vco, editor, project, pl);

        //runProver
        List<String> args = new ArrayList<String>();
        args.add(resolveFile.getPath());
        args.add("-lib");
        args.add(RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());
        args.add("-prove");
        RESOLVECompiler compiler = new RESOLVECompiler(args.toArray(new String[args.size()]));
        compiler.addProverListener(pl);

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Description") {
            @Override
            public void run(@NotNull final ProgressIndicator progressIndicator) {
                compiler.processCommandLineTargets();
            }
        });
    }

    @Nullable
    private VCOutputFile generateVCs(VirtualFile resolveFile, Editor editor, Project project) {
        boolean forceGeneration = true; // from action, they really mean it
        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        "gen vcs");

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(project, resolveFile).getPath());
        argMap.put("-vcs", "");
        gen.addArgs(argMap);
        boolean successful = false;
        try {
            successful = ProgressManager.getInstance().run(gen); //, "Generating", canBeCancelled, e.getData(PlatformDataKeys.PROJECT));
        } catch (Exception e1) {
        }
        if (successful && !editor.isDisposed()) {
            return gen.getVCOutput();
        }
        return null;
    }

    private void presentVCs(VCOutputFile vco, Editor editor, Project project, MyProverListener listener) {
        if (!editor.isDisposed()) {
            MarkupModel markup = editor.getMarkupModel();
            RESOLVEPluginController controller = RESOLVEPluginController.getInstance(project);
            markup.removeAllHighlighters();

            //A mapping from [line number] -> [vc_1, .., vc_j]
            Map<Integer, List<VC>> byLine = vco.getVCsGroupedByLineNumber();
            List<RangeHighlighter> vcRelatedHighlighters = new ArrayList<>();

            for (Map.Entry<Integer, List<VC>> vcsByLine : byLine.entrySet()) {
                List<AnAction> actionsPerVC = new ArrayList<>();
                //create clickable actions for each vc
                for (VC vc : vcsByLine.getValue()) {
                    actionsPerVC.add(new ProverVCAction(listener, vc.getNumber() + "", vc.getExplanation()));
                }

                RangeHighlighter highlighter =
                        markup.addLineHighlighter(vcsByLine.getKey() - 1, HighlighterLayer.ELEMENT_UNDER_CARET, null);
                highlighter.setGutterIconRenderer(new GutterIconRenderer() {
                    @NotNull
                    @Override
                    //clearly something will need to change here if I want this gutter icon animating somehow;
                    //but haven't yet found any good examples of other projects doing this for *gutter icons*...
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
        }
    }

    static class ProverVCAction extends AnAction {
        private final MyProverListener pl;
        private final String vcNum;

        ProverVCAction(MyProverListener l, String vcNum, String explanation) {
            super("VC #" + vcNum + " : " + explanation);
            this.pl = l;
            this.vcNum = vcNum;
        }

        @Override
        public void update(AnActionEvent e) {
            //e.getPresentation()
            if (pl.vcIsProved.containsKey(vcNum) && pl.vcIsProved.get(vcNum)) {
                e.getPresentation().setIcon(RESOLVEIcons.PROVED);
            }
            else {
                e.getPresentation().setIcon(RESOLVEIcons.NOT_PROVED);
            }
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            AbstractTreeBuilder x;
            //TODO
            //controller.getVerifierWindow().show(null);  //open the tool(verifier)window
            //VerifierPanel verifierPanel = controller.getVerifierPanel();

            //I performed an action, let's remember to clean up after ourselves before we go and update
            //the vc panel
            //verifierPanel.setActiveVcPanel(new VerifierPanel.VCPanelMock(project, vc));
        }
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
