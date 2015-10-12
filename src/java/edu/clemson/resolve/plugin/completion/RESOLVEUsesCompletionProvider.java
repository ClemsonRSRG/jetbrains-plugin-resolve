package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResModule;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import edu.clemson.resolve.plugin.psi.impl.ResTypeReference;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReference;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEUsesCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    /** We fiddle around with TextRange so much in here to strip out the
     *  "Intellijidearulezzz" suffix that the completion provider apparently
     *  always feels the need to tack on.
     */
    @Override protected void addCompletions(
            @NotNull CompletionParameters parameters,
            ProcessingContext context,
            @NotNull CompletionResultSet result) {
        ResUsesItem importString = PsiTreeUtil.getParentOfType(
                parameters.getPosition(), ResUsesItem.class);
        if (importString == null) return;
        String path = importString.getText();

        TextRange pathRange = importString.getUsesTextRange()
                .shiftRight(importString.getTextRange().getStartOffset());
        String newPrefix = parameters.getEditor().getDocument()
                .getText(TextRange.create(pathRange.getStartOffset(),
                        parameters.getOffset()));
        result = result.withPrefixMatcher(result.getPrefixMatcher()
                .cloneWithPrefix(newPrefix));

        addCompletions(result, ModuleUtilCore
                .findModuleForPsiElement(parameters.getPosition()),
                parameters.getOriginalFile(), true);
    }

    public static void addCompletions(@NotNull CompletionResultSet result,
                                      @Nullable Module module,
                                      @Nullable PsiElement context,
                                      boolean withLibraries) {
        if (module != null) {
            GlobalSearchScope scope = withLibraries ?
                    RESOLVEUtil.moduleScope(module) :
                    RESOLVEUtil.moduleScopeWithoutLibraries(module);
            for (VirtualFile file : FileTypeIndex
                    .getFiles(RESOLVEFileType.INSTANCE, scope)) {

                Icon fileIcon = RESOLVEIcons.FILE;
                PsiFile psiFile = PsiManager.getInstance(module.getProject()).findFile(file);
                if (psiFile != null && psiFile instanceof ResFile) {
                    ResModule enclosedModule =
                            ((ResFile) psiFile).getEnclosedModule();
                    if (enclosedModule != null) fileIcon = enclosedModule.getIcon(0);
                }
                result.addElement(LookupElementBuilder
                        .create(file.getNameWithoutExtension())
                        .withIcon(fileIcon).withTypeText(file.getName()));
            }
        }
    }

}
