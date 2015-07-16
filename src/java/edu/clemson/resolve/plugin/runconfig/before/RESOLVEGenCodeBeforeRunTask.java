package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.BeforeRunTask;
import com.intellij.openapi.util.text.StringUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

public class RESOLVEGenCodeBeforeRunTask
        extends
            BeforeRunTask<RESOLVEGenCodeBeforeRunTask> {

    @NonNls private static final String COMMAND_ATTRIBUTE = "translate";
    private final String command = "-genCode Java";

    protected RESOLVEGenCodeBeforeRunTask() {
        super(RESOLVEGenCodeBeforeRunTaskProvider.ID);
    }

    public String getTranslateCommand() {
        return command;
    }

    @Override public String toString() {
        return "resolve codegen: " + StringUtil.notNullize(command);
    }

}
