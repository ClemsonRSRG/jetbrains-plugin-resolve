package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.RESOLVELanguage;

public class MyPsiUtils {

    public static PsiElement createLeafFromText(
            Project project, PsiElement context, String text,
            IElementType type) {
        PsiFileFactoryImpl factory =
                (PsiFileFactoryImpl) PsiFileFactory.getInstance(project);
        PsiElement el = factory.createElementFromText(text,
                RESOLVELanguage.INSTANCE,
                type,
                context);
        return PsiTreeUtil.getDeepestFirst(el); // forces parsing of file!!
        // start rule depends on root passed in
    }
}
