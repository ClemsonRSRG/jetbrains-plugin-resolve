package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/** These are written by Terrence Parr, originally used in the antlr4 plugin */
public class MyActionUtils {

    public static int getMouseOffset(MouseEvent mouseEvent, Editor editor) {
        Point point = new Point(mouseEvent.getPoint());
        LogicalPosition pos=editor.xyToLogicalPosition(point);
        return editor.logicalPositionToOffset(pos);
    }

    @NotNull
    public static List<RangeHighlighter> getRangeHighlightersAtOffset(@NotNull Editor editor,
                                                                      int layer,
                                                                      int offset) {
        MarkupModel markupModel = editor.getMarkupModel();
        // collect all highlighters and combine to make a single tool tip
        java.util.List<RangeHighlighter> highlightersAtOffset = new ArrayList<RangeHighlighter>();
        RangeHighlighter[] highlighters = markupModel.getAllHighlighters();
        for (RangeHighlighter r : highlighters) {
            int a = r.getStartOffset();
            int b = r.getEndOffset();
//			System.out.printf("#%d: %d..%d %s\n", i, a, b, r.toString());
            if (offset >= a && offset < b && r.getLayer() == layer) { // cursor is over some kind of highlighting
                highlightersAtOffset.add(r);
            }
        }
        return highlightersAtOffset;
    }

}
