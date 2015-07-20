package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.openapi.util.Key;
import com.intellij.tools.*;

import java.util.List;

public class RESOLVEToolBeforeRunTask
        extends
            AbstractToolBeforeRunTask<RESOLVEToolBeforeRunTask, Tool> {

    protected RESOLVEToolBeforeRunTask() {
        super(RESOLVEToolBeforeRunTaskProvider.ID);
    }

    protected List<Tool> getTools() {
        return ToolManager.getInstance().getTools();
    }
}
