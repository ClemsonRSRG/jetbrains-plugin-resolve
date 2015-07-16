package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.service.execution.ExternalSystemBeforeRunTask;
import com.intellij.openapi.util.Key;

public class RESOLVEGenCodeBeforeRunTask extends ExternalSystemBeforeRunTask {

    public RESOLVEGenCodeBeforeRunTask(
            Key<ExternalSystemBeforeRunTask> providerId,
            ProjectSystemId systemId) {
        super(providerId, systemId);
    }
}
