package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.roots.libraries.DummyLibraryProperties;
import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.LibraryPresentationProvider;
import com.intellij.openapi.roots.libraries.LibraryProperties;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class RESOLVESdkLibraryPresentationProvider
        extends
            LibraryPresentationProvider<DummyLibraryProperties> {

    private static final LibraryKind KIND = LibraryKind.create("resolve");

    public RESOLVESdkLibraryPresentationProvider() {
        super(KIND);
    }

    @Override
    @Nullable
    public Icon getIcon(LibraryProperties e) {
        return RESOLVEIcons.TOOL_ICON;
    }

    @Override
    @Nullable
    public DummyLibraryProperties detect(
            @NotNull List<VirtualFile> classesRoots) {
        for (VirtualFile root : classesRoots) {
            if (ResSmallSdkService.isRESOLVESdkLibRoot(root)) {
                return DummyLibraryProperties.INSTANCE;
            }
        }
        return null;
    }
}
