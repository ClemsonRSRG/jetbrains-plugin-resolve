package edu.clemson.resolve.jetbrains.actions;

import com.intellij.codeInsight.daemon.impl.AnnotationHolderImpl;
import com.intellij.codeInsight.daemon.impl.SeverityRegistrar;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.AnnotationSession;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.impl.DocumentMarkupModel;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtil;
import com.intellij.ui.JBColor;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.AnnotatedModule;
import edu.clemson.resolve.compiler.LanguageSemanticsMessage;
import edu.clemson.resolve.compiler.RESOLVECompilerListener;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.ANTLRMessage;
import org.jetbrains.annotations.NotNull;

import java.util.*;

//Probably going to replace external annotator.. Simplicity is key. Also its nice to still be able to type
//things that the compiler might not currently accept without having a bunch of errors pop up.
//Remember: This isn't a commercial product. It's very much still a research effort.

//Update:
//TODO: Look at ProfilerPanel#selectDecisionInGrammar in the antlrv4 plugin for hints on how to annotate your tree
//based on the results that come back from the compiler.
//This also looks in line with what Peter Gromov (on the open api forums) suggested as well..

//Update2: Antlr v4 plugin: InputPanel#annotateErrorsInPreviewInputEditor
public class AnalyzeAction extends RESOLVEAction {
    public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");

    public static final int ERROR_LAYER = HighlighterLayer.ERROR;
    public static final int WARNING_LAYER = HighlighterLayer.WARNING;


    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(RESOLVEIcons.CHECKMARK);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        if (resolveFile == null || project == null || file == null) return;
        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(e.getProject(), resolveFile).getPath());
        List<String> args = RunRESOLVEOnLanguageFile.getRESOLVEArgsAsList(argMap);
        String fileName = resolveFile.getName();
        args.add(0, fileName);
        RunRESOLVEOnLanguageFile gen =
                new RunRESOLVEOnLanguageFile(resolveFile,
                        project,
                        "Analyzing Sources");
        AnnotatorToolListener issueListener = new AnnotatorToolListener();
        gen.addListener(issueListener);
        gen.addArgs(argMap);
        boolean successful = false;
        try {
            successful = ProgressManager.getInstance().run(gen);
        } catch (Exception e1) {
        }
        if (gen.hasParseErrors()) return;
        editor.getMarkupModel().removeAllHighlighters();    //first

        List<RangeHighlighter> issueRelatedHighlighters = new ArrayList<>();
        for (Issue issue : issueListener.issues) {
            annotateIssueInEditor(resolveFile, issueRelatedHighlighters, editor, issue);
        }
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void beforeDocumentChange(DocumentEvent event) {
            }

            @Override
            public void documentChanged(DocumentEvent event) {
                //remove all current issue related highlighters
                MarkupModel markupModel = editor.getMarkupModel();

                for (RangeHighlighter h : issueRelatedHighlighters) {
                    int eventOffset = event.getOffset();
                    int highlightersOffset = h.getStartOffset();
                    //eventOffset > h.getStartOffset() && eventOffset < h.getStopOffset();
                    if (eventOffset > h.getStartOffset() && eventOffset < h.getEndOffset()) {
                        h.getTextAttributes().setEffectColor(JBColor.ORANGE);
                    }
                    //if (h.getStartOffset() == eventOffset || h.getEndOffset() == eventOffset) {
                        //markupModel.removeHighlighter(h);
                    //}
                }
            }
        });
    }

    //TODO: for hoverover stuff..
    /*public static class MyMouseListener implements EditorMouseListener, EditorMouseMotionListener {

    }*/

    /**
     * Display syntax errors, hints in tooltips if under the cursor
     */
   /* public static void showTooltips(EditorMouseEvent event,
                                    Editor editor,
                                    @NotNull PreviewState previewState, int offset) {
        if ( previewState.parsingResult==null ) return; // no results?

        // Turn off any tooltips if none under the cursor
        // find the highlighter associated with this offset
        List<RangeHighlighter> highlightersAtOffset = MyActionUtils.getRangeHighlightersAtOffset(editor, offset);
        if ( highlightersAtOffset.size()==0 ) {
            return;
        }

        List<String> msgList = new ArrayList<String>();
        boolean foundDecisionEvent = false;
        for (int i = 0; i<highlightersAtOffset.size(); i++) {
            RangeHighlighter r = highlightersAtOffset.get(i);
            DecisionEventInfo eventInfo = r.getUserData(ProfilerPanel.DECISION_EVENT_INFO_KEY);
            String msg;
            if ( eventInfo!=null ) {
                // TODO: move decision event stuff to profiler?
                if ( eventInfo instanceof AmbiguityInfo ) {
                    msg = "Ambiguous upon alts "+eventInfo.configs.getAlts().toString();
                }
                else if ( eventInfo instanceof ContextSensitivityInfo ) {
                    msg = "Context-sensitive";
                }
                else if ( eventInfo instanceof LookaheadEventInfo ) {
                    int k = eventInfo.stopIndex-eventInfo.startIndex+1;
                    msg = "Deepest lookahead k="+k;
                }
                else if ( eventInfo instanceof PredicateEvalInfo ) {
                    PredicateEvalInfo evalInfo = (PredicateEvalInfo) eventInfo;
                    msg = ProfilerPanel.getSemanticContextDisplayString(evalInfo,
                            previewState,
                            evalInfo.semctx, evalInfo.predictedAlt,
                            evalInfo.evalResult);
                    msg = msg+(!evalInfo.fullCtx ? " (DFA)" : "");
                }
                else {
                    msg = "Unknown decision event: "+eventInfo;
                }
                foundDecisionEvent = true;
            }
            else {
                // error tool tips
                SyntaxError errorUnderCursor = r.getUserData(SYNTAX_ERROR);
                msg = getErrorDisplayString(errorUnderCursor);
                if ( msg.length()>MAX_HINT_WIDTH ) {
                    msg = msg.substring(0, MAX_HINT_WIDTH)+"...";
                }
                if ( msg.indexOf('<')>=0 ) {
                    msg = msg.replaceAll("<", "&lt;");
                }
            }
            msgList.add(msg);
        }
        String combinedMsg = Utils.join(msgList.iterator(), "\n");
        HintManagerImpl hintMgr = (HintManagerImpl) HintManager.getInstance();
        if ( foundDecisionEvent ) {
            showDecisionEventToolTip(editor, offset, hintMgr, combinedMsg.toString());
        }
        else {
            showPreviewEditorErrorToolTip(editor, offset, hintMgr, combinedMsg.toString());
        }
    }*/

    public void annotateIssueInEditor(@NotNull VirtualFile file,
                                      @NotNull List<RangeHighlighter> highlighters,
                                      @NotNull Editor editor,
                                      @NotNull Issue issue) {
        MarkupModel markupModel = editor.getMarkupModel();  //ref to the current editor's markup model...
        final TextAttributes attr = new TextAttributes();
        attr.setForegroundColor(JBColor.RED);
        attr.setEffectColor(JBColor.RED);
        attr.setEffectType(EffectType.WAVE_UNDERSCORE);
        Token offendingToken = (Token) issue.msg.offendingToken;

        int layer = 0;
        int a = offendingToken.getStartIndex();
        int b = offendingToken.getStopIndex() + 1;
        if (issue.msg instanceof LanguageSemanticsMessage) {
            layer = HighlighterLayer.ERROR;
        }
        String sourceName = offendingToken.getTokenSource().getSourceName();
        String vFilePath = file.getPath();
        if (vFilePath.equals(sourceName)) { //only want highlights in the doc the user is looking at.
            highlighters.add(markupModel.addRangeHighlighter(a,
                    b,
                    layer, // layer
                    attr,
                    HighlighterTargetArea.EXACT_RANGE));
        }
    }

    public static class Issue {
        String annotation;
        RESOLVEMessage msg;
        public Issue(RESOLVEMessage msg) { this.msg = msg; }
    }

    public static class AnnotatorToolListener implements RESOLVECompilerListener {
        public final List<Issue> issues = new ArrayList<>();

        @Override
        public void info(String s) {
        }

        @Override
        public void error(RESOLVEMessage resolveMessage) {
            issues.add(new Issue(resolveMessage));
        }

        @Override
        public void warning(RESOLVEMessage resolveMessage) {
            issues.add(new Issue(resolveMessage));
        }
    }

}
