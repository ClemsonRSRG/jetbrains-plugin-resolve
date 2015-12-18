package edu.clemson.resolve.jetbrains.editor;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Allows users to click the button on the side panel of the editor and view
 * a hierarchical view of the module they are currently looking at.
 */
public class RESOLVEStructureViewFactory implements PsiStructureViewFactory {

    @Nullable @Override public StructureViewBuilder getStructureViewBuilder(
            @NotNull final PsiFile psiFile) {
        return null;
    }
}
