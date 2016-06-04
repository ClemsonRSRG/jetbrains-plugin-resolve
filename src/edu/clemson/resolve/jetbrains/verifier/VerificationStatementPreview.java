package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.application.options.colors.ColorAndFontSettingsListener;
import com.intellij.application.options.colors.FontEditorPreview;
import com.intellij.application.options.colors.PreviewPanel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.ex.EditorEx;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVEColorsAndFontsPage;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlightingColors;
import org.intellij.images.options.OptionsManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class VerificationStatementPreview implements PreviewPanel {

    //Look at FontEditorPreview as well as NewColorAndFontPanel and SimpleEditorPreview
    private final EditorEx editor;
    public VerificationStatementPreview() {

        this.editor = createPreviewEditor();
        //(EditorEx) FontEditorPreview.createPreviewEditor(stripped, 10, 3, selectedLine, myOptions, false);

    }

    private EditorEx createPreviewEditor() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document editorDocument = editorFactory.createDocument("Max_Length = 0");
        EditorEx editor = (EditorEx) editorFactory.createViewer(editorDocument);

        return null;
    }
    //no use for blinking stuff (yet?)
    @Override
    public void blinkSelectedHighlightType(Object selected) {
    }

    @Override
    public void disposeUIResources() {

    }

    @Override
    public Component getPanel() {
        return null;
    }

    @Override
    public void updateView() {

    }

    @Override
    public void addListener(@NotNull ColorAndFontSettingsListener listener) {

    }
}
