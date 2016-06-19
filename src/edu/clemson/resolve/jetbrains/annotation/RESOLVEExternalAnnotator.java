package edu.clemson.resolve.jetbrains.annotation;

import com.intellij.execution.util.EnvironmentVariable;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileSystemUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.EnvironmentUtil;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.AnnotatedModule;
import edu.clemson.resolve.compiler.CompilerMessage;
import edu.clemson.resolve.compiler.LanguageSemanticsMessage;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import edu.clemson.resolve.jetbrains.actions.RunRESOLVEOnLanguageFile;
import edu.clemson.resolve.jetbrains.sdk.RESOLVESdkService;
import org.antlr.v4.Tool;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.*;
import org.antlr.v4.tool.ast.GrammarAST;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.StringReader;
import java.util.*;

/**
 * Uses the resolve compiler to annotate the actively open editor with error
 * and warning msgs. This code is adapted to our purposes from the ANTLR4
 * intellij plugin located here:
 * <p>
 * <a href="https://github.com/antlr/intellij-plugin-v4">https://github.com/antlr/intellij-plugin-v4/a>
 */
public class RESOLVEExternalAnnotator extends ExternalAnnotator<PsiFile, List<RESOLVEExternalAnnotator.Issue>> {

    public static final Logger LOG = Logger.getInstance("RESOLVEExternalAnnotator");

    public static class Issue {
        String annotation;
        List<Token> offendingTokens = new ArrayList<>();
        RESOLVEMessage msg;

        public Issue(RESOLVEMessage msg) {
            this.msg = msg;
        }
    }

    /** Called first; return file */
    @Override
    @Nullable
    public PsiFile collectInformation(@NotNull PsiFile file) {
        return file;
    }

    @Override
    @Nullable
    public List<RESOLVEExternalAnnotator.Issue> doAnnotate(final PsiFile file) {
        String grammarFileName = file.getVirtualFile().getPath();
        LOG.info("doAnnotate " + grammarFileName);
        String fileContents = file.getText();
        Map<String, String> argMap = new LinkedHashMap<>();

        argMap.put("-lib", RunRESOLVEOnLanguageFile.getContentRoot(file.getProject(), file.getVirtualFile()).getPath());
        List<String> args = RunRESOLVEOnLanguageFile.getRESOLVEArgsAsList(argMap);
        String fileName = file.getName();
        args.add(0, fileName);
        final RESOLVECompiler resolve = new RESOLVECompiler(args.toArray(new String[args.size()]));

        //if lib isn't specified, best we can do is run it on the containing folder
        //for that particular file..
        if (!args.contains("-lib")) {
            // getContainingDirectory() must be identified as a read operation on file system
            ApplicationManager.getApplication().runReadAction(new Runnable() {
                @Override
                public void run() {
                    resolve.libDirectory = file.getVirtualFile().getParent().getCanonicalPath();
                }
            });
        }

        resolve.removeListeners();
        AnnotatorCompilerListener listener = new AnnotatorCompilerListener();
        resolve.addListener(listener);
        try {
            org.antlr.v4.runtime.ANTLRInputStream in = new org.antlr.v4.runtime.ANTLRInputStream(fileContents);
            VirtualFile vfile = file.getVirtualFile();
            in.name = vfile.getPath();
            AnnotatedModule ast = resolve.parseModule(in);
            if (ast == null || ast.hasErrors) return Collections.emptyList();
            resolve.processCommandLineTargets(ast);
          /*
            Map<String, GrammarAST> unusedRules = getUnusedParserRules(g);
            if ( unusedRules!=null ) {
                for (String r : unusedRules.keySet()) {
                    org.antlr.runtime.Token ruleDefToken = unusedRules.get(r).getToken();
                    Issue issue = new Issue(new GrammarInfoMessage(g.fileName, ruleDefToken, r));
                    listener.issues.add(issue);
                }
            }*/

            for (Issue issue : listener.issues) {
                processIssue(file, issue);
            }
        } catch (Exception e) {
            LOG.error("antlr can't process " + file.getName(), e);
        }
        return listener.issues;
    }

    /** Called 3rd */
    @Override
    public void apply(@NotNull PsiFile file,
                      @NotNull List<RESOLVEExternalAnnotator.Issue> issues,
                      @NotNull AnnotationHolder holder) {
        for (Issue issue : issues) {
            for (int j = 0; j < issue.offendingTokens.size(); j++) {
                Token t = issue.offendingTokens.get(j);
                if (t instanceof CommonToken) {
                    CommonToken ct = (CommonToken) t;
                    int startIndex = ct.getStartIndex();
                    int stopIndex = ct.getStopIndex();
                    TextRange range = new TextRange(startIndex, stopIndex + 1);
                    ErrorSeverity severity = ErrorSeverity.INFO;
                    if (issue.msg.getErrorType() != null) {
                        severity = issue.msg.getErrorType().severity;
                    }
                    switch (severity) {
                        case ERROR:
                        case ERROR_ONE_OFF:
                        case FATAL:
                            holder.createErrorAnnotation(range, issue.annotation);
                            break;

                        case WARNING:
                            holder.createWarningAnnotation(range, issue.annotation);
                            break;

                        case WARNING_ONE_OFF:
                        case INFO:
                            holder.createWeakWarningAnnotation(range, issue.annotation);
                        default:
                            break;
                    }
                }
            }
        }
        super.apply(file, issues, holder);
    }


    public void processIssue(final PsiFile file, Issue issue) {
        File languageFile = new File(file.getVirtualFile().getPath());
        File issueFile = new File(issue.msg.fileName);
        if (!languageFile.getName().equals(issueFile.getName())) {
            return; // ignore errors from external files
        }
        ST msgST = null;
        if (issue.msg instanceof LanguageSemanticsMessage) {
            Token t = ((LanguageSemanticsMessage) issue.msg).offendingToken;
            issue.offendingTokens.add(t);
        }
        else if (issue.msg instanceof CompilerMessage) {
            issue.offendingTokens.add(issue.msg.offendingToken);
        }

        RESOLVECompiler antlr = new RESOLVECompiler();
        if (msgST == null) {
            msgST = antlr.errMgr.getMessageTemplate(issue.msg);
        }
        String outputMsg = msgST.render();
        if (antlr.errMgr.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        issue.annotation = outputMsg;
    }
}
