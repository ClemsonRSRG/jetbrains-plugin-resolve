package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.psi.ResolveFileNode;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class ResolveElementFactory {

    private ResolveElementFactory() {
    }

    @NotNull private static ResolveFileNode createFileFromText(
            @NotNull Project project, @NotNull String text) {
        return (ResolveFileNode) PsiFileFactory.getInstance(project)
                .createFileFromText("a.resolve", RESOLVELanguage.INSTANCE, text);
    }

    @NotNull public static PsiElement createIdentifierFromText(
            @NotNull Project project, String text) {
        ResolveFileNode file = createFileFromText(project,
                "Precis "+text+"; end "+text+";");
        return file.getEnclosedModule().getIdentifier();
    }
}
