package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.util.*;

/** These are written by Terrence Parr, originally used in the antlr4 plugin */
public class MyActionUtils {

    public static int getMouseOffset(MouseEvent mouseEvent, Editor editor) {
        Point point = new Point(mouseEvent.getPoint());
        LogicalPosition pos=editor.xyToLogicalPosition(point);
        return editor.logicalPositionToOffset(pos);
    }

    @NotNull
    public static java.util.List<RangeHighlighter> getRangeHighlightersAtOffset(@NotNull Editor editor,
                                                                                int offset) {
        MarkupModel markupModel = editor.getMarkupModel();
        // collect all highlighters and combine to make a single tool tip
        java.util.List<RangeHighlighter> highlightersAtOffset = new ArrayList<RangeHighlighter>();
        for (RangeHighlighter r : markupModel.getAllHighlighters()) {
            int a = r.getStartOffset();
            int b = r.getEndOffset();
//			System.out.printf("#%d: %d..%d %s\n", i, a, b, r.toString());
            if (offset >= a && offset < b) { // cursor is over some kind of highlighting
                highlightersAtOffset.add(r);
            }
        }
        return highlightersAtOffset;
    }

}
