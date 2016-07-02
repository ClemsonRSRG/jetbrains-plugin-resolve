package edu.clemson.resolve.jetbrains.completion;

import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.jetbrains.psi.*;

import java.util.List;

public class MathSymbolInsertHandler extends BasicInsertHandler<LookupElement> {

    private final boolean mathWildcardQuery;

    MathSymbolInsertHandler(boolean mathWildcardQuery) {
        this.mathWildcardQuery = mathWildcardQuery;
    }

    @Override
    public void handleInsert(InsertionContext context, LookupElement item) {
        //this is 4 because that's the length of the current wildcard query keyword: 'this', we want to
        //erase that.. and it comes directly before the symbol (or name) we just inserted.
        if (mathWildcardQuery) {
            context.getDocument().deleteString(context.getStartOffset() - 4, context.getStartOffset());
        }

        Editor editor = context.getEditor();
        PsiElement element = item.getPsiElement();
        if (!(element instanceof ResMathDefnSig)) return;
        ResMathDefnSig signature = (ResMathDefnSig) element;
        int paramsCount = signature.getParameters().size();

        InsertHandler<LookupElement> handler = new BasicInsertHandler<>();
        if (signature instanceof ResMathPrefixDefnSig && paramsCount != 0) {
            handler = ParenthesesInsertHandler.WITH_PARAMETERS;
        }
        else if (signature instanceof ResMathOutfixDefnSig) {
            List<ResMathSymbolName> pieces = ((ResMathOutfixDefnSig) signature).getMathSymbolNameList();
            if (pieces.size() == 2) { //if we actually have a left and right..
                context.getDocument().insertString(context.getStartOffset(), "`");
                context.getDocument().insertString(context.getTailOffset(), pieces.get(1).getText() + "`");
            }
        }
        handler.handleInsert(context, item);
    }
}
