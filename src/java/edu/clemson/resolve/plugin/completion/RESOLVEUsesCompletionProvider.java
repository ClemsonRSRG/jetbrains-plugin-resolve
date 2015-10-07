package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEUsesCompletionProvider
        extends
            CompletionProvider<CompletionParameters> {

    /** We fiddle around with TextRange so much in here to strip out the
     *  "Intellijidearulezzz" suffix that the completion provider apprently
     *  always tacks on.
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
                int i;
                i =0;
                //result.addElement();
                /*VirtualFile parent = file.getParent();
                if (parent == null) continue;
                String importPath = GoSdkUtil.getPathRelativeToSdkAndLibraries(parent, module.getProject(), module);
                if (!StringUtil.isEmpty(importPath) && !importPath.equals(contextImportPath)) {
                    result.addElement(GoCompletionUtil.createPackageLookupElement(importPath, contextImportPath, false));
                }*/
            }
           /* Project project = module.getProject();
            String contextImportPath = GoCompletionUtil.getContextImportPath(context);
            GoExcludedPathsSettings excludedSettings = GoExcludedPathsSettings.getInstance(project);
            GlobalSearchScope scope = withLibraries ? GoUtil.moduleScope(module) : GoUtil.moduleScopeWithoutLibraries(module);
            PsiFile contextFile = context != null ? context.getContainingFile() : null;
            boolean testFileWithTestPackage = GoTestFinder.isTestFileWithTestPackage(contextFile);
            for (VirtualFile file : FileTypeIndex.getFiles(GoFileType.INSTANCE, scope)) {
                VirtualFile parent = file.getParent();
                if (parent == null) continue;
                String importPath = GoSdkUtil.getPathRelativeToSdkAndLibraries(parent, project, module);
                if (!StringUtil.isEmpty(importPath) && !excludedSettings.isExcluded(importPath) &&
                        (testFileWithTestPackage || !importPath.equals(contextImportPath))) {
                    result.addElement(GoCompletionUtil.createPackageLookupElement(importPath, contextImportPath, false));
                }
            }*/
        }
    }
}
