package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class ResElementFactory {

    private ResElementFactory() {
    }

    @NotNull
    private static ResFile createFileFromText(@NotNull Project project, @NotNull String text) {
        return (ResFile) PsiFileFactory.getInstance(project)
                .createFileFromText("a.resolve", RESOLVELanguage.INSTANCE, text);
    }

    @NotNull
    public static PsiElement createIdentifierFromText(@NotNull Project project, String text) {
        ResFile file = createFileFromText(project, "Precis " + text + ";end " + text + ";");
        return file.getEnclosedModule().getIdentifier();
    }

    //TODO: I don't want this to be navigatble.. Figure out how to accomplish this. (or at least make the module
    //navigatable, but obfuscate the body)
    @NotNull
    public static ResFile getHardCodedMathFile(@NotNull Project project) {
        final String hardcoded =
                "Precis HardCoded;\n " +
                        "Definition Cls : HypCls;\n" +
                        "Definition El : Cls;\n" +
                        "Definition Entity : Cls;\n" +
                        "Definition SSet : Cls;\n" +
                        "Definition B : SSet;\n" +
                        "Definition true : B;\n" +
                        "Definition false : B;\n" +
                        "Definition Powerset(S : Cls) : Cls;\n" +
                        "Definition and(a, b : B) : B;\n" +
                        "Definition or(a, b : B) : B;\n" +
                        "Definition not(a : B) : B;\n" +
                        "Definition implies(a, b : B) : B;\n" +
                        "Definition iff(a, b : B) : B;\n" +
                        "end HardCoded;";
        return createFileFromText(project, hardcoded);
    }
}
