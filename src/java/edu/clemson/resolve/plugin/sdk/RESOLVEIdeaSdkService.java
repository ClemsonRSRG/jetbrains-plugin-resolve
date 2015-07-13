package edu.clemson.resolve.plugin.sdk;

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
import edu.clemson.resolve.plugin.RESOLVEModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Most (if not all) of this has been ripped from the go plugin. Once we get our
 * feet on the ground and get something working, we can always  go back through this
 * and sort through (for our purposes) what we truly need and what we don't.
 */
public class RESOLVEIdeaSdkService extends RESOLVESdkService {

    public RESOLVEIdeaSdkService(@NotNull Project project) {
        super(project);
        this.project.getMessageBus().connect(project).subscribe(
                ProjectTopics.PROJECT_ROOTS, new ModuleRootAdapter() {

            @Override public void rootsChanged(ModuleRootEvent event) {
                incModificationCount();
            }
        });
    }

    @Override public String getSdkHomePath(@Nullable final Module module) {
        ComponentManager holder = ObjectUtils.notNull(module, this.project);
        return CachedValuesManager.getManager(this.project).getCachedValue(
                holder, new CachedValueProvider<String>() {

            @Nullable @Override public Result<String> compute() {
                Sdk sdk = getRESOLVESdk(module);
                return Result.create(sdk != null ? sdk.getHomePath() : null,
                        RESOLVEIdeaSdkService.this);
            }
        });
    }

    @Nullable @Override public String getSdkVersion(@Nullable final Module module) {
        ComponentManager holder = ObjectUtils.notNull(module, this.project);
        return CachedValuesManager.getManager(this.project).getCachedValue(
                holder, new CachedValueProvider<String>() {

            @Nullable @Override public Result<String> compute() {
                Sdk sdk = getRESOLVESdk(module);
                return Result.create(sdk != null ? sdk.getVersionString() : null,
                        RESOLVEIdeaSdkService.this);
            }
        });
    }

    @Override public void chooseAndSetSdk(@Nullable final Module module) {
        Sdk projectSdk = ProjectSettingsService.getInstance(this.project)
                    .chooseAndSetSdk();
        if (projectSdk == null && module != null) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override public void run() {
                    if (!module.isDisposed()) {
                        ModuleRootModificationUtil.setSdkInherited(module);
                    }
                }
            });
        }
    }

    @Override public boolean isRESOLVEModule(@Nullable Module module) {
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
        Sdk sdk = ProjectRootManager.getInstance(this.project).getProjectSdk();
        return sdk != null && sdk.getSdkType() instanceof RESOLVESdkType ?
                sdk : null;
    }
}
