package edu.clemson.resolve.jetbrains.annotation;

import edu.clemson.resolve.compiler.RESOLVECompilerListener;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import org.antlr.v4.tool.ANTLRMessage;

import java.util.ArrayList;
import java.util.List;

//TODO: Decompose this into an Annotator IssueListener and VCListener
public class AnnotatorCompilerListener implements RESOLVECompilerListener {

    public final List<RESOLVEExternalAnnotator.Issue> issues = new ArrayList<>();

    @Override public void info(String msg) {
    }

    @Override public void error(RESOLVEMessage msg) {
        issues.add(new RESOLVEExternalAnnotator.Issue(msg));
    }

    @Override public void warning(RESOLVEMessage msg) {
        issues.add(new RESOLVEExternalAnnotator.Issue(msg));
    }
}
