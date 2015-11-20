package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.psi.ResFile;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class ResElementFactory {

    private ResElementFactory() {
    }

    @NotNull private static ResFile createFileFromText(
            @NotNull Project project, @NotNull String text) {
        return (ResFile) PsiFileFactory.getInstance(project)
                .createFileFromText("a.resolve", RESOLVELanguage.INSTANCE, text);
    }

    @NotNull public static PsiElement createIdentifierFromText(
            @NotNull Project project, String text) {
        ResFile file = createFileFromText(project,
                "Precis "+text+";end "+text+";");
        return file.getEnclosedModule().getIdentifier();
    }
}
