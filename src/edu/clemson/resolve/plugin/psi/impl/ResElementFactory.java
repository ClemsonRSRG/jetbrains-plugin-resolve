package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.psi.ResFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
                "Precis " + text + ";end " + text + ";");
        return file.getEnclosedModule().getIdentifier();
    }

    /*@NotNull public static ResUsesListImpl createUsesList(
            @NotNull Project project,
            @Nullable ResUsesListImpl existingUsesList,
            @NotNull String newUsesItemName) {
        List<String> rawStringUsesList = new ArrayList<String>();
        if (existingUsesList != null) {
            for (ResUsesItem usesItem : existingUsesList.getUsesSpecs()) {
                rawStringUsesList.add(usesItem.getText());
            }
        }
        rawStringUsesList.add(newUsesItemName); //now take on the new one.
        String joinedUsesList = StringUtil.join(rawStringUsesList, ", ");
        ResFile file = createFileFromText(project,
                "Precis Temp; uses "+joinedUsesList+"; end Temp;");
        return file.getUsesList();
    }*/
}
