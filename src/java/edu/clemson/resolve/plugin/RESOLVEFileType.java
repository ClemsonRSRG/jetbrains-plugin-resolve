package edu.clemson.resolve.plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

//Todo: Somehow we're going to need this to be able to support multiple file
//types (maybe we can even do different icons for them!)
public class RESOLVEFileType extends LanguageFileType {

    public static final RESOLVEFileType INSTANCE = new RESOLVEFileType();

    private RESOLVEFileType() {
        super(RESOLVELanguage.INSTANCE);
    }

    @NotNull @Override public String getName() {
        return "a RESOLVE file";
    }

    @NotNull @Override public String getDescription() {
        return "RESOLVE Precis file";
    }

    @NotNull @Override public String getDefaultExtension() {
        return "resolve";
    }

    @Nullable @Override public Icon getIcon() {
        return Icons.FILE;
    }
}
