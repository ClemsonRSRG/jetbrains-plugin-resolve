package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResModule;
import edu.clemson.resolve.plugin.psi.ResUsesSpec;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEUsesCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    @Override protected void addCompletions(
            @NotNull CompletionParameters completionParameters,
            ProcessingContext processingContext,
            @NotNull CompletionResultSet completionResultSet) {
        ResUsesSpec usesSpec = PsiTreeUtil.getParentOfType(
                completionParameters.getPosition(), ResUsesSpec.class);
        if (usesSpec == null) return;
        String path = usesSpec.getText();

        TextRange pathRange = usesSpec.getUsesTextRange()
                .shiftRight(usesSpec.getTextRange().getStartOffset());

        String newPrefix = completionParameters.getEditor().getDocument()
                .getText(TextRange.create(pathRange.getStartOffset(),
                        completionParameters.getOffset()));

        completionResultSet = completionResultSet.withPrefixMatcher(
                completionResultSet.getPrefixMatcher()
                        .cloneWithPrefix(newPrefix));

        addCompletions(completionResultSet, ModuleUtilCore
                        .findModuleForPsiElement(
                                completionParameters.getPosition()),
                completionParameters.getOriginalFile(), true);
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
                PsiFile psiFile = PsiManager.getInstance(module.getProject())
                        .findFile(file);
                if (psiFile != null && psiFile instanceof ResFile) {
                    ResModule enclosedModule =
                            ((ResFile) psiFile).getEnclosedModule();
                    if (enclosedModule != null) fileIcon =
                            enclosedModule.getIcon(0);
                }
                result.addElement(LookupElementBuilder
                        .create(file.getNameWithoutExtension())
                        .withIcon(fileIcon).withTypeText(file.getName()));
            }
        }
    }
}
