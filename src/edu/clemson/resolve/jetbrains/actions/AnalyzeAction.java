package edu.clemson.resolve.jetbrains.actions;

import com.intellij.codeInsight.daemon.impl.AnnotationHolderImpl;
import com.intellij.codeInsight.daemon.impl.SeverityRegistrar;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.hint.HintManagerImpl;
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
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtil;
import com.intellij.ui.JBColor;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.*;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.ANTLRMessage;
import org.jetbrains.annotations.NotNull;
import org.stringtemplate.v4.ST;

import java.util.*;

//Update2: Antlr v4 plugin: InputPanel#annotateErrorsInPreviewInputEditor
public class AnalyzeAction extends RESOLVEAction {
    public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");

    public static final Key<Issue> ISSUE_ANNOTATION = Key.create("ISSUE_ANNOTATION");

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
        RESOLVECompiler s;
        Task.Backgroundable x = new Task.Backgroundable() {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                s.processCommandLineTargets();
            }
        }
        AnnotatorToolListener issueListener = new AnnotatorToolListener();
        gen.addListener(issueListener);
        gen.addArgs(argMap);
        ErrorManager err = gen.getErrorManager();
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
        editor.addEditorMouseMotionListener(new EditorMouseMotionListener() {
            @Override
            public void mouseMoved(EditorMouseEvent e) {
                int offset = MyActionUtils.getMouseOffset(e.getMouseEvent(), editor);
                if (offset >= editor.getDocument().getTextLength()) return;
                List<RangeHighlighter> highlightersAtOffset =
                        MyActionUtils.getRangeHighlightersAtOffset(editor, offset);
                List<String> msgs = new ArrayList<String>();

                for (RangeHighlighter highlighter : highlightersAtOffset) {
                    Issue errorUnderCursor = highlighter.getUserData(ISSUE_ANNOTATION);
                    String x =  getIssueDisplayString(errorUnderCursor);
                    int i;
                    i=0;
                    /*msg = getErrorDisplayString(errorUnderCursor);
                    if ( msg.length()>MAX_HINT_WIDTH ) {
                        msg = msg.substring(0, MAX_HINT_WIDTH)+"...";
                    }*/
                }

                //showTooltips()
            }

            @Override
            public void mouseDragged(EditorMouseEvent e) {
            }
        });
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

    public static String getIssueDisplayString(Issue e) {
        ST msg = e.msg.getMessageTemplate(false);
        Token t = e.msg.offendingToken;
        String result = "line " + t.getLine() + ":" + t.getCharPositionInLine() + " " + msg.render();
        return result;
    }

    public static void showErrorToolTip(Editor editor, int offset, HintManager hintMgr, String msg) {
        int flags = HintManager.HIDE_BY_ANY_KEY | HintManager.HIDE_BY_TEXT_CHANGE | HintManager.HIDE_BY_SCROLLING;
        int timeout = 0; // default?
        hintMgr.showErrorHint(editor, msg, offset, offset + 1, HintManager.ABOVE, flags, timeout);
    }

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
            RangeHighlighter highlighter = markupModel.addRangeHighlighter(a,
                    b,
                    layer, // layer
                    attr,
                    HighlighterTargetArea.EXACT_RANGE);
            highlighters.add(highlighter);
            highlighter.putUserData(ISSUE_ANNOTATION, issue);
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
