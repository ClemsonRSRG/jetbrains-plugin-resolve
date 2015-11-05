package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class QualifierInsertHandler extends BasicInsertHandler<LookupElement> {
    private final String insertStr;

    /** Set to {@code true} if we should insert one character of leading
     *  whitespace before (and immediately after) {@code insertStr};
     *  {@code false} otherwise.
     */
    private final boolean shouldPad;

    public QualifierInsertHandler(String aStr, boolean pad) {
        this.insertStr = aStr;
        this.shouldPad = pad;
    }

    @Override public void handleInsert(@NotNull InsertionContext context,
                                       LookupElement item) {
        Editor editor = context.getEditor();
        int tailOffset = context.getTailOffset();
        Document document = editor.getDocument();
        context.commitDocument();
        boolean staysAtChar = document.getTextLength() > tailOffset &&
               String.valueOf(document.getCharsSequence().charAt(tailOffset))
                       .startsWith(insertStr);

        context.setAddCompletionChar(false);
        if (!staysAtChar) {

            document.insertString(tailOffset+1, insertStr);
        }
        editor.getCaretModel().moveToOffset(shouldPad ? tailOffset + 3 :
                tailOffset + 2);

        AutoPopupController.getInstance(context.getProject())
                .scheduleAutoPopup(editor);
    }
}
