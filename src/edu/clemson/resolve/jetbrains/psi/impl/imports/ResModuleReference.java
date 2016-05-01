package edu.clemson.resolve.jetbrains.psi.impl.imports;

import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiFileSystemItemProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import edu.clemson.resolve.jetbrains.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/** Represents a reference to some directory (or a specific {@link PsiFile} file).
 *  This code is adapted to our purposes from the intellij go language
 *  plugin located here:
 *  <p>
 *  <a href="https://github.com/go-lang-plugin-org/go-lang-idea-plugin">https://github.com/go-lang-plugin-org/go-lang-idea-plugin/a>
 */
class ResModuleReference extends FileReference {

    ResModuleReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @Override
    public PsiFileSystemItem resolve() {
        final PsiFileSystemItem result = super.resolve();
        if (result instanceof ResFile) {
            return result;
        }
        return null;
    }

    //TREAT the 'from' clause into myReferences in the completion set (at the end) then you'll have a convenient,
    //automatically searcheable context.

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive,
                                           @NotNull PsiFile file) {
        //there's got to be a real way of searching for extensioned filetypes... geez
        String referenceText = getText();

        Set<ResolveResult> result = ContainerUtil.newLinkedHashSet();
        Set<ResolveResult> innerResult = ContainerUtil.newLinkedHashSet();
        Collection<PsiFileSystemItem> ctxs = getContexts();
        //TODO: Ok, getContexts() needs to return only the Std context... (and the context representing the current proj)
        for (PsiFileSystemItem context : ctxs) {

            //if (!(context instanceof PsiDirectory)) continue;
            //first resolve the top level context
            //innerResolveInContext(referenceText, context, innerResult, caseSensitive);
            //PsiDirectory[] ctxSubdirectories = ((PsiDirectory) context).getSubdirectories();

            //for (PsiDirectory subctx : ctxSubdirectories) {
                //now resolve into each subdirectory we find (this is going to be as deep as we search)
                innerResolveInContext(referenceText, context, innerResult, caseSensitive);
           // }

            for (ResolveResult resolveResult : innerResult) {
                PsiElement element = resolveResult.getElement();
                if (element instanceof ResFile) {
                    result.add(resolveResult);
                }
            }
        }
        return result.isEmpty() ? ResolveResult.EMPTY_ARRAY :
                result.toArray(new ResolveResult[result.size()]);
    }

    @Override
    protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof ResFile) {
            return RESOLVECompletionUtil.createResolveFileLookupElement((ResFile) candidate);
        }
        return super.createLookupItem(candidate);
    }

    /*@Override
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
    }*/
}
