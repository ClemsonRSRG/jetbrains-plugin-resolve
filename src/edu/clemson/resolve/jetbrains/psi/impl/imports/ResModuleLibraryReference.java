package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.completion.RESOLVECompletionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/** Represents a reference to some top level library directory. This is distinct from {@link ResModuleReference}, which
 *  is otherwise used to reference some {@link edu.clemson.resolve.jetbrains.psi.ResFile}.
 *
 *  @author dtwelch
 */
class ResModuleLibraryReference extends FileReference {

    ResModuleLibraryReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive, @NotNull PsiFile file) {
        String referenceText = getText();
        Set<ResolveResult> result = ContainerUtil.newLinkedHashSet();
        Set<ResolveResult> innerResult = ContainerUtil.newLinkedHashSet();
        for (PsiFileSystemItem context : getContexts()) {
            innerResolveInContext(referenceText, context, innerResult, caseSensitive);
            for (ResolveResult resolveResult : innerResult) {
                PsiElement element = resolveResult.getElement();
                if (element instanceof PsiDirectory) {
                    result.add(resolveResult);
                }
            }
        }
        return result.isEmpty() ? ResolveResult.EMPTY_ARRAY : result.toArray(new ResolveResult[result.size()]);
    }

    @Override
    protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof PsiDirectory) {
            return RESOLVECompletionUtil.createDirectoryLookupElement((PsiDirectory)candidate);
        }
        return super.createLookupItem(candidate);
    }
}
