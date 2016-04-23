package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateSettings;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVEKeywordCompletionProvider
        extends
        CompletionProvider<CompletionParameters> {

    public static final InsertHandler<LookupElement> EMPTY_INSERT_HANDLER =
            new InsertHandler<LookupElement>() {
                @Override
                public void handleInsert(InsertionContext context,
                                         LookupElement element) {
                }
            };

    private final int priority;
    @Nullable
    private final InsertHandler<LookupElement> insertHandler;
    @Nullable
    private final AutoCompletionPolicy completionPolicy;
    @NotNull
    private final String[] keywords;

    public RESOLVEKeywordCompletionProvider(int priority, String... keywords) {
        this(priority, null, null, keywords);
    }

    public RESOLVEKeywordCompletionProvider(int priority,
                                            @Nullable AutoCompletionPolicy completionPolicy,
                                            @NotNull String... keywords) {
        this(priority, null, completionPolicy, keywords);
    }

    public RESOLVEKeywordCompletionProvider(int priority,
                                            @Nullable InsertHandler<LookupElement> insertHandler,
                                            @NotNull String... keywords) {
        this(priority, insertHandler, null, keywords);
    }

    public RESOLVEKeywordCompletionProvider(int priority,
                                            @Nullable InsertHandler<LookupElement> insertHandler,
                                            @Nullable AutoCompletionPolicy completionPolicy,
                                            @NotNull String... keywords) {
        this.priority = priority;
        this.insertHandler = insertHandler;
        this.completionPolicy = completionPolicy;
        this.keywords = keywords;
    }

    @Override
    protected void addCompletions(
            @NotNull CompletionParameters parameters,
            ProcessingContext context,
            @NotNull CompletionResultSet result) {
        for (String keyword : keywords) {
            result.addElement(createKeywordLookupElement(keyword));
        }
    }

    @NotNull
    private LookupElement createKeywordLookupElement(
            @NotNull final String keyword) {
        final InsertHandler<LookupElement> handler =
                ObjectUtils.chooseNotNull(this.insertHandler,
                        createTemplateBasedInsertHandler("resolve_lang_" + keyword));
        LookupElement result =
                createKeywordLookupElement(keyword, priority, handler);
        return completionPolicy != null ?
                completionPolicy.applyPolicy(result) : result;
    }

    public static LookupElement createKeywordLookupElement(
            @NotNull final String keyword, int priority,
            @Nullable InsertHandler<LookupElement> insertHandler) {
        LookupElementBuilder builder = LookupElementBuilder.create(keyword)
                .withBoldness(true).withInsertHandler(insertHandler);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @Nullable
    public static InsertHandler<LookupElement>
    createTemplateBasedInsertHandler(
            @NotNull final String templateId) {
        return new InsertHandler<LookupElement>() {
            @Override
            public void handleInsert(@NotNull InsertionContext context,
                                     LookupElement item) {
                Template template = TemplateSettings.getInstance()
                        .getTemplateById(templateId);
                Editor editor = context.getEditor();
                if (template != null) {
                    editor.getDocument().deleteString(context.getStartOffset(),
                            context.getTailOffset());
                    TemplateManager.getInstance(context.getProject())
                            .startTemplate(editor, template);
                } else {
                    final int currentOffset = editor.getCaretModel().getOffset();
                    final CharSequence documentText = editor.getDocument()
                            .getImmutableCharSequence();
                    if (documentText.length() <= currentOffset ||
                            documentText.charAt(currentOffset) != ' ') {
                        EditorModificationUtil.insertStringAtCaret(editor, " ");
                    } else {
                        EditorModificationUtil.moveCaretRelatively(editor, 1);
                    }
                }
            }
        };
    }
}
