package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEAutoUsesInsertHandler<T extends ResNamedElement>
        implements
            InsertHandler<LookupElement> {
    public static final InsertHandler<LookupElement> SIMPLE_INSERT_HANDLER =
            new RESOLVEAutoUsesInsertHandler<ResNamedElement>();
    @Nullable private final Function<T, InsertHandler<LookupElement>> delegateGetter;
    @Nullable private final Class<T> clazz;

    public RESOLVEAutoUsesInsertHandler() {
        this(((Function<T, InsertHandler<LookupElement>>)null), null);
    }

    public RESOLVEAutoUsesInsertHandler(
            @Nullable final InsertHandler<LookupElement> delegate,
            @Nullable Class<T> clazz) {
        this(new Function<T, InsertHandler<LookupElement>>() {
            @Override
            public InsertHandler<LookupElement> fun(T o) {
                return delegate;
            }
        }, clazz);
    }

    public RESOLVEAutoUsesInsertHandler(
            @Nullable Function<T, InsertHandler<LookupElement>> delegateGetter,
            @Nullable Class<T> clazz) {
        this.delegateGetter = delegateGetter;
        this.clazz = clazz;
    }

    @Override public void handleInsert(InsertionContext context,
                                       LookupElement item) {
        PsiElement element = item.getPsiElement();
        if (element instanceof ResNamedElement) {
            if (clazz != null && delegateGetter != null && clazz.isInstance(element)) {
                InsertHandler<LookupElement> handler =
                        delegateGetter.fun(clazz.cast(element));
                if (handler != null) {
                    handler.handleInsert(context, item);
                }
            }
            autoImport(context, (ResNamedElement)element);
        }
    }

    private static void autoImport(@NotNull InsertionContext context,
                                   @NotNull ResNamedElement element) {
        PsiFile file = context.getFile();
        if (!(file instanceof ResFile)) return;

       /* String usesName = element.getContainingFile().getImportPath();
        if (StringUtil.isEmpty(fullPackageName)) return;

        GoImportSpec existingImport = ((GoFile)file).getImportedPackagesMap().get(fullPackageName);
        if (existingImport != null) return;

        PsiDocumentManager.getInstance(context.getProject()).commitDocument(context.getEditor().getDocument());*/
        ((ResFile)file).addUses("TEST");
    }


}
