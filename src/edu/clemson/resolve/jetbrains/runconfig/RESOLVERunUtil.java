package edu.clemson.resolve.jetbrains.runconfig;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.psi.ResFile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVERunUtil {

    private RESOLVERunUtil() {
    }

    public static void installGoWithMainFileChooser(final Project project,
                                                    @NotNull TextFieldWithBrowseButton fileField) {
        installFileChooser(project, fileField, false, false, new Condition<VirtualFile>() {
            @Override
            public boolean value(VirtualFile file) {
                if (file.getFileType() != RESOLVEFileType.INSTANCE) {
                    return false;
                }
                return isMainGoFile(PsiManager.getInstance(project).findFile(file));
            }
        });
    }

    @Contract("null -> false")
    public static boolean isMainGoFile(@Nullable PsiFile psiFile) {
        if (psiFile instanceof ResFile) {
            return ((ResFile)psiFile).hasMainOperationWithBody();
        }
        return false;
    }

    public static void installFileChooser(@NotNull Project project,
                                          @NotNull ComponentWithBrowseButton field,
                                          boolean directory) {
        installFileChooser(project, field, directory, false);
    }

    public static void installFileChooser(@NotNull Project project, @NotNull ComponentWithBrowseButton field, boolean directory,
                                          boolean showFileSystemRoots) {
        installFileChooser(project, field, directory, showFileSystemRoots, null);
    }

    public static void installFileChooser(@NotNull Project project,
                                          @NotNull ComponentWithBrowseButton field,
                                          boolean directory,
                                          boolean showFileSystemRoots,
                                          @Nullable Condition<VirtualFile> fileFilter) {
        FileChooserDescriptor chooseDirectoryDescriptor = directory
                ? FileChooserDescriptorFactory.createSingleFolderDescriptor()
                : FileChooserDescriptorFactory.createSingleLocalFileDescriptor();
        chooseDirectoryDescriptor.setRoots(project.getBaseDir());
        chooseDirectoryDescriptor.setShowFileSystemRoots(showFileSystemRoots);
        chooseDirectoryDescriptor.withFileFilter(fileFilter);
        if (field instanceof TextFieldWithBrowseButton) {
            ((TextFieldWithBrowseButton)field).addBrowseFolderListener(new TextBrowseFolderListener(chooseDirectoryDescriptor, project));
        }
        else {
            //noinspection unchecked
            field.addBrowseFolderListener(project, new ComponentWithBrowseButton.BrowseFolderActionListener(null, null, field, project,
                    chooseDirectoryDescriptor,
                    TextComponentAccessor.TEXT_FIELD_WITH_HISTORY_WHOLE_TEXT));
        }
    }
}
