package edu.clemson.resolve.plugin.completion;

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
        @Override public void handleInsert(InsertionContext context,
                                           LookupElement element) {
        }
    };

    private final int myPriority;
    @Nullable  private final InsertHandler<LookupElement> myInsertHandler;
    @Nullable private final AutoCompletionPolicy myCompletionPolicy;
    @NotNull private final String[] myKeywords;

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
        myPriority = priority;
        myInsertHandler = insertHandler;
        myCompletionPolicy = completionPolicy;
        myKeywords = keywords;
    }

    @Override protected void addCompletions(
            @NotNull CompletionParameters parameters, ProcessingContext context,
                                  @NotNull CompletionResultSet result) {
        for (String keyword : myKeywords) {
            result.addElement(createKeywordLookupElement(keyword));
        }
    }

    @NotNull private LookupElement createKeywordLookupElement(
            @NotNull String keyword) {
        if (keyword.contains(" ")) {
            keyword = keyword.replace(" ", "_");
        }
        final InsertHandler<LookupElement> insertHandler =
                ObjectUtils.chooseNotNull(myInsertHandler,
                createTemplateBasedInsertHandler("resolve_lang_" + keyword));
        LookupElement result = createKeywordLookupElement(keyword,
                myPriority, insertHandler);
        return myCompletionPolicy != null ?
                myCompletionPolicy.applyPolicy(result) : result;
    }

    public static LookupElement createKeywordLookupElement(
            @NotNull final String keyword, int priority,
            @Nullable InsertHandler<LookupElement> insertHandler) {
        LookupElementBuilder builder = LookupElementBuilder.create(keyword)
                .withBoldness(true).withInsertHandler(insertHandler);
        return PrioritizedLookupElement.withPriority(builder, priority);
    }

    @Nullable public static InsertHandler<LookupElement>
        createTemplateBasedInsertHandler(@NotNull final String templateId) {
        return new InsertHandler<LookupElement>() {
            @Override public void handleInsert(
                    @NotNull InsertionContext context, LookupElement item) {
                Template template = TemplateSettings.getInstance()
                        .getTemplateById(templateId);
                Editor editor = context.getEditor();
                if (template != null) {
                    editor.getDocument().deleteString(context.getStartOffset(),
                            context.getTailOffset());
                    TemplateManager.getInstance(context.getProject())
                            .startTemplate(editor, template);
                }
                else {
                    final int currentOffset = editor.getCaretModel().getOffset();
                    final CharSequence documentText = editor.getDocument()
                            .getImmutableCharSequence();
                    if (documentText.length() <= currentOffset ||
                            documentText.charAt(currentOffset) != ' ') {
                        EditorModificationUtil.insertStringAtCaret(editor, " ");
                    }
                    else {
                        EditorModificationUtil.moveCaretRelatively(editor, 1);
                    }
                }
            }
        };
    }
}
