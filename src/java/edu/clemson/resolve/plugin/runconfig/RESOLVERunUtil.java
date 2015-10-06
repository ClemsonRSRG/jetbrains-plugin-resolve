package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.ide.scratch.ScratchFileType;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.psi.FileNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVERunUtil {

    public static boolean isMainRESOLVEFile(PsiFile psiFile) {
        if (psiFile != null && psiFile instanceof FileNode) {
            return ((FileNode)psiFile).holdsValidExecutableModule();
        }
        return false;
    }

    public static void installRESOLVEWithMainFileChooser(final Project project,
                                @NotNull TextFieldWithBrowseButton fileField) {
        installFileChooser(project, fileField, false, new Condition<VirtualFile>() {
            @Override public boolean value(VirtualFile file) {
                if (file.getFileType() != RESOLVEFileType.INSTANCE) {
                    return false;
                }
                final PsiFile psiFile = PsiManager.getInstance(project)
                        .findFile(file);
                return isMainRESOLVEFile(psiFile);
            }
        });
    }

    public static void installFileChooser(@NotNull Project project,
            @NotNull TextFieldWithBrowseButton field, boolean directory) {
        installFileChooser(project, field, directory, null);
    }

    public static void installFileChooser(@NotNull Project project,
            @NotNull TextFieldWithBrowseButton field, boolean directory,
            @Nullable Condition<VirtualFile> fileFilter) {
        FileChooserDescriptor chooseDirectoryDescriptor = directory
            ? FileChooserDescriptorFactory.createSingleFolderDescriptor()
            : FileChooserDescriptorFactory.createSingleLocalFileDescriptor();
        chooseDirectoryDescriptor.setRoots(project.getBaseDir());
        chooseDirectoryDescriptor.setShowFileSystemRoots(false);
        chooseDirectoryDescriptor.withFileFilter(fileFilter);
        field.addBrowseFolderListener(
                new TextBrowseFolderListener(chooseDirectoryDescriptor));
    }

    @Nullable public static PsiElement getContextElement(
            @Nullable ConfigurationContext context) {
        if (context == null) return null;

        PsiElement psiElement = context.getPsiLocation();
        if (psiElement == null || !psiElement.isValid()) return null;

        FileIndexFacade indexFacade =
                FileIndexFacade.getInstance(psiElement.getProject());
        PsiFileSystemItem psiFile = psiElement instanceof PsiFileSystemItem ?
                (PsiFileSystemItem)psiElement : psiElement.getContainingFile();
        VirtualFile file = psiFile.getVirtualFile();
        if (file.getFileType() != ScratchFileType.INSTANCE &&
                (!indexFacade.isInContent(file) || indexFacade.isExcludedFile(file))) {
            return null;
        }
        return psiElement;
    }
}
