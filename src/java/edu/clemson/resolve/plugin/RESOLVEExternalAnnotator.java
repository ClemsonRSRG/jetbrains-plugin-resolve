package edu.clemson.resolve.plugin;

import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiFile;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import org.antlr.v4.runtime.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RESOLVEExternalAnnotator
        extends
            ExternalAnnotator<PsiFile, List<RESOLVEExternalAnnotator.Issue>> {

    public static final Logger LOG = Logger.getInstance("RESOLVEExternalAnnotator");

    public static class Issue {
        String annotation;
        List<Token> offendingTokens = new ArrayList<Token>();
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

    @Nullable @Override public List<RESOLVEExternalAnnotator.Issue> doAnnotate(
            final PsiFile file) {
        String moduleFileName = file.getVirtualFile().getPath();
        LOG.info("doAnnotate "+moduleFileName);
        String fileContents = file.getText();

    }
}
