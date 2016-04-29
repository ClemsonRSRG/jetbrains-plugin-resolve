package edu.clemson.resolve.jetbrains.editor;

import com.intellij.ide.structureView.*;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

/** Allows users to click the button on the side panel of the editor and view
 *  a hierarchical view of the module they are currently looking at.
 */
//TODO: Clearly I haven't done this yet..
public class RESOLVEStructureViewFactory implements PsiStructureViewFactory {

    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(PsiFile psiFile) {
        return null;
    }
}