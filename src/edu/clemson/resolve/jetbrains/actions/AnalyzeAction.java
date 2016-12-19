package edu.clemson.resolve.jetbrains.actions;

import com.intellij.codeInsight.daemon.impl.AnnotationHolderImpl;
import com.intellij.codeInsight.daemon.impl.SeverityRegistrar;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.hint.HintManagerImpl;
import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.AnnotationSession;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
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
import com.intellij.ui.LightweightHint;
import com.intellij.ui.awt.RelativePoint;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.*;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.RESOLVEPluginController;
import edu.clemson.resolve.misc.Utils;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.ANTLRMessage;
import org.antlr.v4.tool.ErrorSeverity;
import org.jetbrains.annotations.NotNull;
import org.stringtemplate.v4.ST;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class AnalyzeAction extends RESOLVEAction {

    public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");
    public static final Key<Issue> ISSUE_ANNOTATION = Key.create("ISSUE_ANNOTATION");

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(RESOLVEIcons.CHECKMARK);
    }

    //TODO: Maybe factor out the annotation code in this method eventually to a static method that
    //can be invoked from other actions... For example, I would like to somehow run this same analyze
    //action when users want to gen vcs (for example).
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        VirtualFile resolveFile = getRESOLVEFileFromEvent(event);
        if (project == null || resolveFile == null) return;

        commitDoc(project, resolveFile);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        Map<String, String> argMap = new LinkedHashMap<>();
        argMap.put("", resolveFile.getName());
        argMap.put("-lib", getContentRoot(project, resolveFile).getPath());

        RESOLVECompiler compiler = getDefaultCompiler(argMap);
        ConsoleView console = RESOLVEPluginController.getInstance(project).getConsole();
        console.clear();
        String timeStamp = getTimeStamp();
        console.print(timeStamp + ": resolve " + Utils.join(getArgMapAsList(argMap), " ") + "\n",
                ConsoleViewContentType.SYSTEM_OUTPUT);

        RunRESOLVEListener defaultListener = new RunRESOLVEListener(compiler, console);
        AnnotatorToolListener issueListener = new AnnotatorToolListener();
        compiler.removeListeners();
        compiler.addListeners(defaultListener, issueListener);
        try {
            ProgressManager.getInstance().run(new Task.WithResult<Boolean, Exception>(
                    project, "Analyzing Current Source", false) {
                @Override
                protected Boolean compute(@NotNull ProgressIndicator indicator) throws Exception {
                    compiler.processCommandLineTargets();
                    return compiler.errMgr.getErrorCount() == 0;
                }
            });
        }
        catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String msg = sw.toString();
            Notification notification =
                    new Notification("AnalyzeAction", "failed to execute " + resolveFile.getPath(),
                            e.toString(),
                            NotificationType.INFORMATION);
            Notifications.Bus.notify(notification, project);
            console.print(timeStamp + ": resolve " + msg + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
            defaultListener.hasOutput = true; // show console below
        }
        if (defaultListener.hasOutput) {
            RESOLVEPluginController.showConsoleWindow(project);
        }
        if (compiler.commandlineTargets.get(0).hasParseErrors) return;
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
                if (highlightersAtOffset.size() == 0) return;
                List<String> msgs = new ArrayList<String>();

                for (RangeHighlighter highlighter : highlightersAtOffset) {
                    Issue errorUnderCursor = highlighter.getUserData(ISSUE_ANNOTATION);
                    if (errorUnderCursor == null || errorUnderCursor.msg == null) continue;
                    String errorMsg = getIssueDisplayString(compiler, errorUnderCursor);
                    if (errorMsg != null) msgs.add(errorMsg);
                }
                String combinedErrorMsg = Utils.join(msgs, "\n");
                showErrorToolTip(editor, offset, HintManager.getInstance(), combinedErrorMsg, e);
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

    public static String getIssueDisplayString(RESOLVECompiler compiler, Issue e) {
        ST st = compiler.errMgr.getMessageTemplate(e.msg);
        return st.render();
    }

    public static void showErrorToolTip(Editor editor, int offset, HintManager hintMgr, String msg, EditorMouseEvent event) {
        int flags = HintManager.HIDE_BY_ANY_KEY | HintManager.HIDE_BY_TEXT_CHANGE | HintManager.HIDE_BY_SCROLLING;
        int timeout = 0; // default?
        hintMgr.showErrorHint(editor, msg, offset, offset + 1, HintManager.ABOVE, flags, timeout);
    }

    public static void annotateIssueInEditor(@NotNull VirtualFile file,
                                             @NotNull List<RangeHighlighter> highlighters,
                                             @NotNull Editor editor,
                                             @NotNull Issue issue) {
        MarkupModel markupModel = editor.getMarkupModel();  //ref to the current editor's markup model...
        final TextAttributes attr = new TextAttributes();

        Token offendingToken = (Token) issue.msg.offendingToken;

        int layer = 0;
        int a = offendingToken.getStartIndex();
        int b = offendingToken.getStopIndex() + 1;
        if (issue.msg instanceof LanguageSemanticsMessage) {
            if (issue.msg.getErrorType().severity == ErrorSeverity.ERROR) {
                attr.setForegroundColor(JBColor.RED);
                attr.setEffectColor(JBColor.RED);
                attr.setEffectType(EffectType.WAVE_UNDERSCORE);
            }
            else {  //warning (should be yellowish or something)
                attr.setBackgroundColor(new JBColor(new Color(246, 235, 188),
                        new Color(246, 235, 188)));
                //attr.setEffectColor(JBColor.RED);
                attr.setEffectType(EffectType.BOXED);
            }
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
