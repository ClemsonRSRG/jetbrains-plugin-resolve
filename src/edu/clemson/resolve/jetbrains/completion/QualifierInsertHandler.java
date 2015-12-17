package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class QualifierInsertHandler extends BasicInsertHandler<LookupElement> {
    private final String insertStr;

    public QualifierInsertHandler(String aStr, boolean pad) {
        this.insertStr = pad ? " " + aStr + " " : aStr;
    }

    @Override public void handleInsert(@NotNull InsertionContext context,
                                       LookupElement item) {
        Editor editor = context.getEditor();
        int tailOffset = context.getTailOffset();
        Document document = editor.getDocument();
        context.commitDocument();
        boolean staysAtChar = document.getTextLength() > tailOffset &&
               String.valueOf(document.getCharsSequence().charAt(tailOffset))
                       .equals(insertStr);

        context.setAddCompletionChar(false);
        if (!staysAtChar) {
            document.insertString(tailOffset, insertStr);
        }
        editor.getCaretModel().moveToOffset(tailOffset + insertStr.length());

        AutoPopupController.getInstance(context.getProject())
                .scheduleAutoPopup(editor);
    }
}
