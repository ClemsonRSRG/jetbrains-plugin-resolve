package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.application.options.colors.*;
import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.NotNullProducer;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVEColorsAndFontsPage;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlighter;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlightingColors;
import org.intellij.images.options.OptionsManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SimpleVerificationEditorPreview implements PreviewPanel {

    //Here are some classes worth keeping in mind:
    // -FontEditorPreview as well as NewColorAndFontPanel and SimpleEditorPreview
    private final EditorEx editor;

    @NotNull private final Disposable myDisposable = Disposer.newDisposable();

    public SimpleVerificationEditorPreview(String verificationText) {
        this.editor = createPreviewEditor(verificationText);
        //(EditorEx) FontEditorPreview.createPreviewEditor(stripped, 10, 3, selectedLine, myOptions, false);

    }

    private EditorEx createPreviewEditor(String content) {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document editorDocument = editorFactory.createDocument(content);

        EditorEx editor = (EditorEx) editorFactory.createViewer(editorDocument);
        //editor.setBorder(new EmptyBorder(10, 10, 10, 10)); //TODO: this line seems to work, but doesn't seem like
        //"the right way" to add some margins to our editor. Keep it in mind though in case no other soln turns up.


        EditorColorsScheme colorScheme = EditorColorsManager.getInstance().getGlobalScheme();
        editor.setBackgroundColor(Gray._237);   //TODO: Change this to a JBColor
        editor.setColorsScheme(colorScheme);

        EditorHighlighter highlighter = HighlighterFactory.createHighlighter(getSyntaxHighlighter(), colorScheme);
        editor.setHighlighter(highlighter);

        EditorSettings settings = editor.getSettings();
        settings.setLineNumbersShown(false);
        settings.setWhitespacesShown(true);
        settings.setLineMarkerAreaShown(false);
        settings.setIndentGuidesShown(false);
        settings.setFoldingOutlineShown(false);
        settings.setBlinkCaret(false);
        settings.setCaretRowShown(false);
        settings.setLeadingWhitespaceShown(true);

        editor.setEmbeddedIntoDialogWrapper(true);
        return editor;
    }

    //no use for blinking stuff (yet?)
    @Override
    public void blinkSelectedHighlightType(Object selected) {
    }

    @Override
    public void disposeUIResources() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        editorFactory.releaseEditor(editor);
    }

    @Override
    public Component getPanel() {
        return editor.getComponent();
    }

    @Override
    public void updateView() {
        editor.reinitSettings();
    }

    @NotNull
    SyntaxHighlighter getSyntaxHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    @Override
    public void addListener(@NotNull ColorAndFontSettingsListener listener) {
    }
}
