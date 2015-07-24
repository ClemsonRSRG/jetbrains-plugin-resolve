package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.roots.libraries.DummyLibraryProperties;
import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.LibraryPresentationProvider;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class RESOLVESdkLibraryProvider
        extends
            LibraryPresentationProvider<DummyLibraryProperties> {

    private static final LibraryKind KIND = LibraryKind.create("RESOLVE");

    public RESOLVESdkLibraryProvider() {
        super(KIND);
    }

    @Nullable public Icon getIcon() {
        return RESOLVEIcons.MODULE;
    }

    @Nullable public DummyLibraryProperties detect(
            @NotNull List<VirtualFile> classesRoots) {
        for (VirtualFile root : classesRoots) {
            if (RESOLVESmallIDEsSdkService.isRESOLVESdkLibRoot(root)) {
                return DummyLibraryProperties.INSTANCE;
            }
        }
        return null;
    }
}
