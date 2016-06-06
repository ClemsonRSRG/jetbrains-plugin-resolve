package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

//call {@link #addNotify()} after this object is created (and {@link #removeNotify} when this object is done being
//used
public class VerificationEditorPreview extends EditorTextField {

    public VerificationEditorPreview(Project project, String content) {
        super(EditorFactory.getInstance().createDocument(content), project, RESOLVEFileType.INSTANCE, true);
        setBackground(Gray._237);    //Figure out a way to make this work with changes to LAF
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOneLineMode(false);
    }

    @Override
    protected boolean shouldHaveBorder() {
        return false;
    }

    @Override
    protected EditorEx createEditor() {
        EditorEx e = super.createEditor();
        e.setBorder(new EmptyBorder(4, 4, 4, 4));
        return e;
    }
}
