package edu.clemson.resolve.plugin.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.DelegatingGlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEUtil {

    @NotNull public static GlobalSearchScope moduleScopeWithoutLibraries(
            @NotNull Module module) {
        return GlobalSearchScope.moduleWithDependenciesScope(module)
                .uniteWith(module.getModuleContentWithDependenciesScope());
    }

    @NotNull public static GlobalSearchScope moduleScope(
            @NotNull PsiElement element) {
        return moduleScope(element.getProject(), ModuleUtilCore
                .findModuleForPsiElement(element));
    }

    @NotNull public static GlobalSearchScope moduleScope(
            @NotNull Project project, @Nullable Module module) {
        return module != null ? moduleScope(module) :
                GlobalSearchScope.projectScope(project);
    }

    @NotNull public static GlobalSearchScope moduleScope(
            @NotNull Module module) {
        return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module)
                .uniteWith(module.getModuleContentWithDependenciesScope());
    }

    private static class AllScope extends DelegatingGlobalSearchScope {
        public AllScope(@NotNull GlobalSearchScope baseScope) {
            super(baseScope);
        }

        @Override public boolean contains(@NotNull VirtualFile file) {
            return super.contains(file);
        }
    }
}
