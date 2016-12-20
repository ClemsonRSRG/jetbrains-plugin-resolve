package edu.clemson.resolve.jetbrains.actions;

import edu.clemson.resolve.compiler.RESOLVECompilerListener;
import edu.clemson.resolve.compiler.RESOLVEMessage;

import java.util.ArrayList;
import java.util.List;

public class CompilerIssueListener implements RESOLVECompilerListener {
    public final List<AnalyzeAction.Issue> issues = new ArrayList<>();

    @Override
    public void info(String s) {
    }

    @Override
    public void error(RESOLVEMessage resolveMessage) {
        issues.add(new AnalyzeAction.Issue(resolveMessage));
    }

    @Override
    public void warning(RESOLVEMessage resolveMessage) {
        issues.add(new AnalyzeAction.Issue(resolveMessage));
    }
}
