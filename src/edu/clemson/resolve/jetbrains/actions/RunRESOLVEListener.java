package edu.clemson.resolve.jetbrains.actions;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import edu.clemson.resolve.compiler.RESOLVECompiler;
import edu.clemson.resolve.compiler.RESOLVECompilerListener;
import edu.clemson.resolve.compiler.RESOLVEMessage;
import org.antlr.v4.tool.ANTLRMessage;
import org.jetbrains.annotations.NotNull;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;

public class RunRESOLVEListener implements RESOLVECompilerListener {

    public final List<String> all = new ArrayList<String>();
    private final RESOLVECompiler compiler;
    private final ConsoleView console;
    public boolean hasOutput = false;

    public RunRESOLVEListener(@NotNull RESOLVECompiler compiler,
                              @NotNull ConsoleView console) {
        this.compiler = compiler;
        this.console = console;
    }

    @Override public void info(String msg) {
        if (compiler.errMgr.formatWantsSingleLineMessage()) {
            msg = msg.replace('\n', ' ');
        }
        console.print(msg+"\n", ConsoleViewContentType.NORMAL_OUTPUT);
        hasOutput = true;
    }

    @Override public void error(RESOLVEMessage msg) {
        track(msg, ConsoleViewContentType.ERROR_OUTPUT);
    }

    @Override public void warning(RESOLVEMessage msg) {
        track(msg, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    private void track(RESOLVEMessage msg, ConsoleViewContentType errType) {
        ST msgST = compiler.errMgr.getMessageTemplate(msg);
        String outputMsg = msgST.render();
        if (compiler.errMgr.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        console.print(outputMsg+"\n", errType);
        hasOutput = true;
    }
}
