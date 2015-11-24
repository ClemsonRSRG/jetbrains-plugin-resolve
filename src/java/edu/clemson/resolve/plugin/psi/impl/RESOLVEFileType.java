package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.fileTypes.LanguageFileType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * The {@link LanguageFileType} for RESOLVE files.
 *
 * @author <dtw.welch@gmail.com>
 * @since 0.0.1
 */
public class RESOLVEFileType extends LanguageFileType {

    public static final RESOLVEFileType INSTANCE = new RESOLVEFileType();

    private RESOLVEFileType() {
        super(RESOLVELanguage.INSTANCE);
    }

    @NotNull @Override public String getName() {
        return "RESOLVE file";
    }

    @NotNull @Override public String getDescription() {
        return "RESOLVE file";
    }

    @NotNull @Override public String getDefaultExtension() {
        return "resolve";
    }

    @Nullable @Override public Icon getIcon() {
        return RESOLVEIcons.FILE;
    }
}
