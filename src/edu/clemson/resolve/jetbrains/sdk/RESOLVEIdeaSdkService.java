package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.ProjectTopics;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.ui.configuration.ProjectSettingsService;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.ObjectUtils;
import edu.clemson.resolve.jetbrains.RESOLVEModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEIdeaSdkService extends RESOLVESdkService {

    public RESOLVEIdeaSdkService(@NotNull Project project) {
        super(project);
        project.getMessageBus().connect(project).subscribe(
                ProjectTopics.PROJECT_ROOTS, new ModuleRootAdapter() {
                    @Override
                    public void rootsChanged(ModuleRootEvent event) {
                        incModificationCount();
                    }
                });
    }

    @Override
    public String getSdkHomePath(@Nullable final Module module) {
        ComponentManager holder = ObjectUtils.notNull(module, project);
        return CachedValuesManager.getManager(project).getCachedValue(holder,
                new CachedValueProvider<String>() {
                    @Nullable
                    @Override
                    public Result<String> compute() {
                        Sdk sdk = getRESOLVESdk(module);
                        return Result.create(sdk != null ? sdk.getHomePath() : null,
                                RESOLVEIdeaSdkService.this);
                    }
                });
    }

    @Nullable
    @Override
    public String getSdkVersion(@Nullable final Module module) {
        String parentVersion = super.getSdkVersion(module);
        if (parentVersion != null) {
            return parentVersion;
        }

        ComponentManager holder = ObjectUtils.notNull(module, project);
        return CachedValuesManager.getManager(project).getCachedValue(holder,
                new CachedValueProvider<String>() {
                    @Nullable
                    @Override
                    public Result<String> compute() {
                        Sdk sdk = getRESOLVESdk(module);
                        return Result.create(sdk != null ? sdk.getVersionString() :
                                null, RESOLVEIdeaSdkService.this);
                    }
                });
    }

    @Override
    public void chooseAndSetSdk(@Nullable final Module module) {
        Sdk projectSdk = ProjectSettingsService.getInstance(project)
                .chooseAndSetSdk();
        if (projectSdk == null && module != null) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    if (!module.isDisposed()) {
                        ModuleRootModificationUtil.setSdkInherited(module);
                    }
                }
            });
        }
    }

    @Override
    public boolean isRESOLVEModule(@Nullable Module module) {
        return super.isRESOLVEModule(module) &&
                ModuleUtil.getModuleType(module) == RESOLVEModuleType.getInstance();
    }

    private Sdk getRESOLVESdk(@Nullable Module module) {
        if (module != null) {
            Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
            if (sdk != null && sdk.getSdkType() instanceof RESOLVESdkType) {
                return sdk;
            }
        }
        Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
        return sdk != null && sdk.getSdkType() instanceof RESOLVESdkType ? sdk : null;
    }
}
