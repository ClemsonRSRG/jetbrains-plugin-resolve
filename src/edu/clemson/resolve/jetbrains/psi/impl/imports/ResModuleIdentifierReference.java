package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/** Represents a reference to some directory (or a specific resolve file).
 *  This code is adapted to our purposes from the intellij go language
 *  plugin located here:
 *  <p>
 *  <a href="https://github.com/go-lang-plugin-org/go-lang-idea-plugin">https://github.com/go-lang-plugin-org/go-lang-idea-plugin/a>
 */
public class ResModuleIdentifierReference extends FileReference {

    public ResModuleIdentifierReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @Override
    public PsiFileSystemItem resolve() {
        final PsiFileSystemItem result = super.resolve();
        if (result instanceof ResFile || result instanceof PsiDirectory) {
            return result;
        }
        return null;
    }

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive,
                                           @NotNull PsiFile file) {
        String referenceText = getText();

        Set<ResolveResult> result = ContainerUtil.newLinkedHashSet();
        Set<ResolveResult> innerResult = ContainerUtil.newLinkedHashSet();
        Collection<PsiFileSystemItem> ctxs = getContexts();
        for (PsiFileSystemItem context : getContexts()) {
            innerResolveInContext(referenceText, context, innerResult, caseSensitive);
            for (ResolveResult resolveResult : innerResult) {
                PsiElement element = resolveResult.getElement();
                if (element instanceof PsiDirectory || element instanceof ResFile) {
                    return new ResolveResult[]{resolveResult};

                    /** if (isLast()) {
                         return new ResolveResult[]{resolveResult};
                     }**/
                    //result.add(resolveResult);
                }

            }
            innerResult.clear();
        }
        return result.isEmpty() ? ResolveResult.EMPTY_ARRAY :
                result.toArray(new ResolveResult[result.size()]);
    }

    @Override
    protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof PsiDirectory) {
            return RESOLVECompletionUtil.createDirectoryLookupElement((PsiDirectory) candidate);
        }
        return super.createLookupItem(candidate);
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        if (super.isReferenceTo(element)) {
            return true;
        }

        if (element instanceof PsiDirectoryContainer) {
            for (PsiDirectory directory : ((PsiDirectoryContainer) element).getDirectories()) {
                if (super.isReferenceTo(directory)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element, boolean absolute) throws IncorrectOperationException {
        if (!absolute) {
            FileReference firstReference = ArrayUtil.getFirstElement(getFileReferenceSet().getAllReferences());
            if (firstReference != null) {
                Collection<PsiFileSystemItem> contexts = getFileReferenceSet().getDefaultContexts();
                for (ResolveResult resolveResult : firstReference.multiResolve(false)) {
                    PsiElement resolveResultElement = resolveResult.getElement();
                    if (resolveResultElement instanceof PsiFileSystemItem) {
                        PsiFileSystemItem parentDirectory = ((PsiFileSystemItem) resolveResultElement).getParent();
                        if (parentDirectory != null && contexts.contains(parentDirectory)) {
                            return getElement();
                        }
                    }
                }
            }
        }
        return super.bindToElement(element, absolute);
    }
}
