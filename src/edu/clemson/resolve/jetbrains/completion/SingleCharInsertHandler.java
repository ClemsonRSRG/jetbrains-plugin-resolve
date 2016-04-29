package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/** Allows anything that is a single character into the editor.
 *  This code is from the intellij go language plugin located here:
 *  <p>
 *  <a href="https://github.com/go-lang-plugin-org/go-lang-idea-plugin">https://github.com/go-lang-plugin-org/go-lang-idea-plugin/a>
 */
class SingleCharInsertHandler extends BasicInsertHandler<LookupElement> {

    private final char character;

    SingleCharInsertHandler(char aChar) {
        this.character = aChar;
    }

    @Override
    public void handleInsert(@NotNull InsertionContext context, LookupElement item) {
        Editor editor = context.getEditor();
        int tailOffset = context.getTailOffset();
        Document document = editor.getDocument();
        context.commitDocument();
        boolean staysAtChar = document.getTextLength() > tailOffset &&
                document.getCharsSequence().charAt(tailOffset) == character;

        context.setAddCompletionChar(false);
        if (!staysAtChar) {
            document.insertString(tailOffset, String.valueOf(character));
        }
        editor.getCaretModel().moveToOffset(tailOffset + 1);
        AutoPopupController.getInstance(context.getProject()).scheduleAutoPopup(editor);
    }
}
