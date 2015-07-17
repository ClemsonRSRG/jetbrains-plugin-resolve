package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.BeforeRunTask;
import com.intellij.openapi.util.text.StringUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

public class RESOLVECommandBeforeRunTask
        extends
            BeforeRunTask<RESOLVECommandBeforeRunTask> {

    private String command;
    @NonNls private static final String COMMAND_ATTRIBUTE = "command";

    protected RESOLVECommandBeforeRunTask() {
        super(RESOLVEBeforeRunTaskProvider.ID);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override public void writeExternal(Element element) {
        super.writeExternal(element);
        if (this.command != null) element.setAttribute(COMMAND_ATTRIBUTE, command);
    }

    @Override public void readExternal(Element element) {
        super.readExternal(element);
        final String command = element.getAttributeValue(COMMAND_ATTRIBUTE);
        if (command != null) this.command = command;
    }

    @Override public String toString() {
        return "resolve " + StringUtil.notNullize(command);
    }

}
