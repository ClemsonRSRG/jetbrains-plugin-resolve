package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiElementFilter;
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

    public static PsiElement findDeclNodeAbove(
            AbstractNamedElementRefNode element, final String ruleName) {
        ModuleBlockNode moduleEls =
                PsiTreeUtil.getContextOfType(element, ModuleBlockNode.class);
        return findDeclNode(ruleName, moduleEls);
    }

    public static PsiElement findDeclNode(final String ruleName,
                                              ModuleBlockNode rules) {
        PsiElementFilter declNode = new PsiElementFilter() {
            @Override
            public boolean isAccepted(PsiElement element) {
                PsiElement nameNode = element.getFirstChild();
                if ( nameNode==null ) return false;
                return (element instanceof TypeModelDeclNode &&
                        nameNode.getText().equals(ruleName));
            }
        };
        PsiElement[] ruleSpec = PsiTreeUtil.collectElements(rules, declNode);
        if ( ruleSpec.length>0 ) return ruleSpec[0];
        return null;
    }
}
