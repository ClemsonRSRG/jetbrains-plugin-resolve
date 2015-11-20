package edu.clemson.resolve.plugin;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link FileTypeFactory} responsible for registering {@link RESOLVEFileType} with the system.
 *
 * @since 0.1
 */
public class RESOLVEFileTypeFactory extends FileTypeFactory {

    @Override public void createFileTypes(
            @NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(RESOLVEFileType.INSTANCE, "resolve");
    }
}
