package edu.clemson.resolve.plugin.psi.impl.uses;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.completion.RESOLVECompletionUtil;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import edu.clemson.resolve.plugin.psi.ResUsesItem;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResScopeProcessor;
import edu.clemson.resolve.plugin.sdk.RESOLVESdkUtil;
import edu.clemson.resolve.plugin.util.RESOLVEUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ResUsesReference extends FileReference {

    public ResUsesReference(@NotNull FileReferenceSet fileReferenceSet,
                            TextRange range, int index, String text) {
        super(fileReferenceSet, range, index, text);
    }

    @Override protected Object createLookupItem(PsiElement candidate) {
        if (candidate instanceof PsiDirectory) {
            return RESOLVECompletionUtil
                    .createDirectoryLookupElement((PsiDirectory) candidate);
        }
        return super.createLookupItem(candidate);
    }

    public PsiFileSystemItem resolve() {
        PsiDirectory sourceFile = getDirectory();
        Collection<PsiFileSystemItem> contexts =
                this.getFileReferenceSet().getDefaultContexts();

        for (PsiFileSystemItem f : contexts) {
            String text = getText();
            VirtualFile x = f.getVirtualFile().findChild(getText()+".resolve");
            if (x == null) continue;
            PsiFile file = f.getManager().findFile(x);

            if (file != null) {
                PsiElementResolveResult result =
                        new PsiElementResolveResult(FileReference.getOriginalFile(file));
                return (PsiFileSystemItem) result.getElement();
            }
        }
        return null;
    }

    public boolean processResolveVariants(@NotNull CompletionResultSet set) {

        return false;
    }

    @Nullable private PsiDirectory getDirectory() {
        PsiElement originalElement = CompletionUtil.getOriginalElement(getElement());
        PsiFile file = originalElement != null ? originalElement.getContainingFile() : getElement().getContainingFile();
        return file.getParent();
    }
}
