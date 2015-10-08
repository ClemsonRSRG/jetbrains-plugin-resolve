package edu.clemson.resolve.plugin.util;

import com.intellij.openapi.module.Module;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

public class RESOLVEUtil {

    @NotNull public static GlobalSearchScope moduleScopeWithoutLibraries(
            @NotNull Module module) {
        return GlobalSearchScope.moduleWithDependenciesScope(module)
                .uniteWith(module.getModuleContentWithDependenciesScope());
    }

    @NotNull public static GlobalSearchScope moduleScope(
            @NotNull Module module) {
        return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module)
                .uniteWith(module.getModuleContentWithDependenciesScope());
    }
}
