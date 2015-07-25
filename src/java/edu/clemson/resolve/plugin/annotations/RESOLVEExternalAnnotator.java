package edu.clemson.resolve.plugin.annotations;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.compiler.LanguageSemanticsMessage;
import edu.clemson.resolve.compiler.LanguageSyntaxMessage;
import edu.clemson.resolve.compiler.RESOLVECompiler;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import edu.clemson.resolve.plugin.parsing.RunRESOLVEOnModuleFile;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RESOLVEExternalAnnotator
        extends
            ExternalAnnotator<PsiFile, List<RESOLVEExternalAnnotator.Issue>> {

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
    @Override @Nullable public PsiFile collectInformation(
            @NotNull PsiFile file) {
        return file;
    }

    /** Called 2nd; run resolve on file */
    @Nullable @Override public List<RESOLVEExternalAnnotator.Issue> doAnnotate(
            final PsiFile file) {
        String moduleFileName = file.getVirtualFile().getPath();
        LOG.info("doAnnotate "+moduleFileName);
        String fileContents = file.getText();
        List<String> args = RunRESOLVEOnModuleFile
                .getRESOLVEArgsAsList(file.getProject(), file.getVirtualFile());
        args.add(moduleFileName);
        final RESOLVECompiler resolve =
                new RESOLVECompiler(args.toArray(new String[args.size()]));
        AnnotatorCompilerListener listener = new AnnotatorCompilerListener();
        resolve.addListener(listener);
        try {
            resolve.processCommandLineTargets();

            System.out.println("ISSUES SIZE: " + listener.issues.size());
            for (Issue issue : listener.issues) {
                System.out.println(issue.msg.toString());
            }
            for (Issue issue : listener.issues) {
                processIssue(file, issue);
            }
        }
        catch (Exception e) {
            LOG.error("resolve can't process "+file.getName(), e);
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
                if ( t instanceof CommonToken) {
                    CommonToken ct = (CommonToken)t;
                    int startIndex = ct.getStartIndex();
                    int stopIndex = ct.getStopIndex();
                    TextRange range = new TextRange(startIndex, stopIndex + 1);
                    switch (issue.msg.getErrorType().severity) {
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
        File moduleFile = new File(file.getVirtualFile().getPath());

        if ( issue.msg instanceof LanguageSemanticsMessage) {
            Token t = ((LanguageSemanticsMessage)issue.msg).offendingToken;
            issue.offendingTokens.add(t);
        }
        else if ( issue.msg instanceof LanguageSyntaxMessage) {
            Token t = issue.msg.offendingToken;
            //issue.offendingTokens.add(t);
        }
        else if ( issue.msg instanceof RESOLVEMessage ) {
            issue.offendingTokens.add(issue.msg.offendingToken);
        }

        RESOLVECompiler resolve = new RESOLVECompiler();
        ST msgST = resolve.errMgr.getMessageTemplate(issue.msg);
        String outputMsg = msgST.render();
        if (resolve.errMgr.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        issue.annotation = outputMsg;
    }
}
