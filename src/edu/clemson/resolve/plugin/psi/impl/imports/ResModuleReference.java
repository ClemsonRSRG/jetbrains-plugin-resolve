package edu.clemson.resolve.plugin.psi.impl.imports;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileSystemUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceCompletion;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.util.ArrayUtil;
import com.intellij.util.io.fs.FileSystem;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResMathReferenceExp;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

//TODO: This shit ain't right atm.. eh.
public class ResModuleReference extends FileReference {

    public ResModuleReference(@NotNull FileReferenceSet fileReferenceSet,
                              TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override public PsiFileSystemItem resolve() {
       /* PsiDirectory sourceFile = getDirectory();
        Collection<PsiFileSystemItem> contexts =
                this.getFileReferenceSet().getDefaultContexts();

        PsiFile[] foundFiles =
                FilenameIndex.getFilesByName(sourceFile.getProject(),
                        getText() + ".resolve",
                RESOLVEUtil.moduleScope(getDirectory()));

        if (foundFiles.length != 0) {
            PsiFile file = foundFiles[0];
            PsiElementResolveResult result =
                    new PsiElementResolveResult(FileReference.getOriginalFile(file));
            return (PsiFileSystemItem) result.getElement();
        }
        return null;*/
        PsiDirectory sourceFile = getDirectory();
        Collection<PsiFileSystemItem> contexts =
                this.getFileReferenceSet().getDefaultContexts();
        PsiFile[] foundFiles =
                FilenameIndex.getFilesByName(sourceFile.getProject(),
                        getText() + ".resolve",
                        RESOLVEUtil.moduleScope(getDirectory()));
        if (foundFiles.length != 0) {
            PsiFile file = foundFiles[0];
            PsiElementResolveResult result =
                    new PsiElementResolveResult(FileReference.getOriginalFile(file));
            return (PsiFileSystemItem) result.getElement();
        }
        /*for (PsiFileSystemItem f : contexts) {

            String text = getText();
            VirtualFile x = f.getVirtualFile().findChild(getText() + ".resolve");
            if (x == null) continue;
            PsiFile file = f.getManager().findFile(x);

            if (file != null) {
                PsiElementResolveResult result =
                        new PsiElementResolveResult(FileReference.getOriginalFile(file));
                return (PsiFileSystemItem) result.getElement();
            }
        }*/
        return null;
    }

   /* public boolean processResolveVariants(@NotNull CompletionResultSet set) {
        return true;
    }*/

    @Override protected Object createLookupItem(PsiElement candidate) {
        return null;/*LookupElementBuilder
                .create(((ResFile)candidate).getVirtualFile().getNameWithoutExtension())
                .withIcon(candidate.getIcon(0)).withTypeText(
                        ((ResFile) candidate).getName());*/

    }

    @Nullable private PsiDirectory getDirectory() {
        PsiElement originalElement =
                CompletionUtil.getOriginalElement(getElement());
        PsiFile file = originalElement != null ?
                originalElement.getContainingFile() :
                getElement().getContainingFile();
        return file.getParent();
    }
}
