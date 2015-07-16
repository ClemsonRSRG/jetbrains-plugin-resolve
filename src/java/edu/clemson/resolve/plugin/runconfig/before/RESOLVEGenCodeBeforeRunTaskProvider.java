package edu.clemson.resolve.plugin.runconfig.before;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.service.execution.ExternalSystemBeforeRunTask;
import com.intellij.openapi.externalSystem.service.execution.ExternalSystemBeforeRunTaskProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.Nullable;

public class RESOLVEGenCodeBeforeRunTaskProvider extends ExternalSystemBeforeRunTaskProvider {

    public static final Key<RESOLVEGenCodeBeforeRunTask> ID = Key.create("RESOLVEGenCodeBeforeRunTask");

    public RESOLVEGenCodeBeforeRunTaskProvider(ProjectSystemId systemId,
                       Project project, Key<ExternalSystemBeforeRunTask> id) {
        super(systemId, project, id);
    }

    @Nullable
    @Override
    public ExternalSystemBeforeRunTask createTask(RunConfiguration runConfiguration) {
        return new RESOLVEGenCodeBeforeRunTask();
    }
}
