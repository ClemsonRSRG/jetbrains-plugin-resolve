package java.edu.clemson.resolve.plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PrecisFileType extends LanguageFileType {

    public static final SimpleFileType INSTANCE = new SimpleFileType();

    @NotNull
    @Override
    public String getName() {
        return "RESOLVE Precis file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "RESOLVE Precis file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return null;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
