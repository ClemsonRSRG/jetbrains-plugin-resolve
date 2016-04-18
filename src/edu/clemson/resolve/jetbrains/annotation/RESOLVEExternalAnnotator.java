package edu.clemson.resolve.jetbrains.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.RESOLVECompiler;
import edu.clemson.resolve.compiler.AnnotatedModule;
import edu.clemson.resolve.compiler.CompilerMessage;
import edu.clemson.resolve.compiler.LanguageSemanticsMessage;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import edu.clemson.resolve.jetbrains.actions.RunRESOLVEOnLanguageFile;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.Tool;
import org.antlr.v4.runtime.CharStream;
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

public class RESOLVEExternalAnnotator
        extends
            ExternalAnnotator<PsiFile, List<RESOLVEExternalAnnotator.Issue>> {

    public static final Logger LOG =
            Logger.getInstance("RESOLVEExternalAnnotator");

    public static class Issue {
        String annotation;
        List<Token> offendingTokens = new ArrayList<>();
        RESOLVEMessage msg;
        public Issue(RESOLVEMessage msg) { this.msg = msg; }
    }

    /** Called first; return file */
    @Override @Nullable public PsiFile collectInformation(
            @NotNull PsiFile file) {
        return file;
    }

    /** Called 2nd; run antlr on file */
    @Nullable @Override public List<RESOLVEExternalAnnotator.Issue> doAnnotate(
            final PsiFile file) {
        String grammarFileName = file.getVirtualFile().getPath();
        LOG.info("doAnnotate "+grammarFileName);
        String fileContents = file.getText();
        List<String> args = RunRESOLVEOnLanguageFile
                .getRESOLVEArgsAsList(
                        file.getProject(), file.getVirtualFile());
        String fileName = file.getName();
        args.add(0, fileName);
        final RESOLVECompiler resolve =
                new RESOLVECompiler(args.toArray(new String[args.size()]));
        if ( !args.contains("-lib") ) {
            // getContainingDirectory() must be identified as a read operation on file system
            ApplicationManager.getApplication().runReadAction(new Runnable() {
                @Override
                public void run() {
                    //TODO: Clean that line up... needs to be virtual file.
                    resolve.workingDirectory = file.getVirtualFile().getParent().getCanonicalPath();
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

            if ( ast==null || ast.hasErrors ) return Collections.emptyList();

            //TODO: Set filename for 'ast'

            if ( vfile==null ) {
                LOG.error("doAnnotate no virtual file for "+file);
                return listener.issues;
            }


            ast.fileName = vfile.getPath();
            resolve.processCommandLineTargets(ast);

          /*  g.fileName = vfile.getPath();
            antlr.process(g, false);

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
        }
        catch (Exception e) {
            LOG.error("antlr can't process "+file.getName(), e);
        }
        return listener.issues;
    }


    /** Called 3rd */
    @Override public void apply(@NotNull PsiFile file,
                                List<RESOLVEExternalAnnotator.Issue> issues,
                                @NotNull AnnotationHolder holder) {
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            for (int j = 0; j < issue.offendingTokens.size(); j++) {
                Token t = issue.offendingTokens.get(j);
                if ( t instanceof org.antlr.v4.runtime.CommonToken ) {
                    org.antlr.v4.runtime.CommonToken ct = (org.antlr.v4.runtime.CommonToken)t;
                    int startIndex = ct.getStartIndex();
                    int stopIndex = ct.getStopIndex();
                    TextRange range = new TextRange(startIndex, stopIndex + 1);
                    ErrorSeverity severity = ErrorSeverity.INFO;
                    if ( issue.msg.getErrorType()!=null ) {
                        severity = issue.msg.getErrorType().severity;
                    }
                    switch ( severity ) {
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
        if ( !languageFile.getName().equals(issueFile.getName()) ) {
            return; // ignore errors from external files
        }
        ST msgST = null;
        /*if ( issue.msg instanceof GrammarInfoMessage ) { // not in ANTLR so must hack it in
            org.antlr.runtime.Token t = ((GrammarSemanticsMessage)issue.msg).offendingToken;
            issue.offendingTokens.add(t);
            msgST = new ST("unused parser rule <arg>");
            msgST.add("arg", t.getText());
            msgST.impl.name = "info";
        }*/
        if ( issue.msg instanceof LanguageSemanticsMessage ) {
            Token t = ((LanguageSemanticsMessage)issue.msg).offendingToken;
            issue.offendingTokens.add(t);
        }
        else if ( issue.msg instanceof CompilerMessage ) {
            issue.offendingTokens.add(issue.msg.offendingToken);
        }

        RESOLVECompiler antlr = new RESOLVECompiler();
        if ( msgST==null ) {
            msgST = antlr.errMgr.getMessageTemplate(issue.msg);
        }
        String outputMsg = msgST.render();
        if ( antlr.errMgr.formatWantsSingleLineMessage() ) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        issue.annotation = outputMsg;
    }
}
