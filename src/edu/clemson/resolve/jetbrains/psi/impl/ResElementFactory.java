package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                "Precis Builtin_Class_Theory;\n " +
                    "Definition Cls : HypCls;\n\n" +
                    "Definition El : Cls;\n\n" +
                    "Definition Entity : Cls;\n\n" +
                    "Definition SSet : Cls;\n\n" +
                    "Definition B : SSet;\n\n" +
                    "Definition true : B;\n\n" +
                    "Definition false : B;\n\n" +
                    "Definition Powerset(S : Cls) : Cls;\n\n" +
                    "Definition ∧(a, b : B) : B;\n\n" +
                    "Definition ∨(a, b : B) : B;\n\n" +
                    "Definition ¬(a : B) : B;\n\n" +
                    "Definition ⟹(a, b : B) : B;\n\n" +
                    //"Definition iff(a, b : B) : B;\n" +
                "end Builtin_Class_Theory;";
        return createFileFromText(project, hardcoded);
    }
}
