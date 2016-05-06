package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVELanguage;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.ResUsesSpecGroup;
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

    //if we do implicit imports the odd way we'll have to do something behind the scenes like construct the nodes
    //manually.. ugh.
/*
    @NotNull
    public static ResUsesSpecGroup createUsesSpecGroup(@NotNull Project project, @NotNull String importString,
                                                       @Nullable String alias, boolean withParens) {
        importString = GoPsiImplUtil.isQuotedImportString(importString) ? importString : StringUtil.wrapWithDoubleQuote(importString);
        alias = alias != null ? alias + " " : "";
        GoFile file = createFileFromText(project, withParens
                ? "package main\nimport (\n" + alias + importString + "\n)"
                : "package main\nimport " + alias + importString);
        return PsiTreeUtil.findChildOfType(file, GoImportDeclaration.class);
    }

    @NotNull
    public static GoImportSpec createImportSpec(@NotNull Project project, @NotNull String importString, @Nullable String alias) {
        GoImportDeclaration importDeclaration = createImportDeclaration(project, importString, alias, true);
        return ContainerUtil.getFirstItem(importDeclaration.getImportSpecList());
    }

    @NotNull
    public static GoImportString createImportString(@NotNull Project project, @NotNull String importString) {
        GoImportSpec importSpec = createImportSpec(project, importString, null);
        return importSpec.getImportString();
    }
*/
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
