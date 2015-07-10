package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.AdditionalDataConfigurable;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.projectRoots.SdkModificator;
import com.intellij.openapi.projectRoots.SdkType;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

public class RESOLVELanguageSdkType extends SdkType {

    public static final File DEFAULT_SDK_PATH_WINDOWS = new File("c:/RESOLVE/");
    public static final File DEFAULT_SDK_PATH_OSX = new File("/usr/local/opt/RESOLVE");
    public static final File DEFAULT_SDK_PATH_LINUX = new File("/usr/");

    // Messages go to the log available in Help -> Show log in finder.
    private final static Logger LOG = Logger.getInstance(RESOLVELanguageSdkType.class);

    public DLanguageSdkType() {
        super(JpsDLanguageModelSerializerExtension.DLANGUAGE_SDK_TYPE_ID);
    }

    /**
     * Returns the RESOLVE Language SDK.
     */
    @NotNull public static RESOLVELanguageSdkType getInstance() {
        return SdkType.findInstance(RESOLVELanguageSdkType.class);
    }

    /**
     * Returns the icon to be used for RESOLVE things in general.
     */
    @Override public Icon getIcon() {
        return RESOLVEIcons.FILE;
    }

    @Nullable @Override public AdditionalDataConfigurable
                createAdditionalDataConfigurable(
            SdkModel sdkModel, SdkModificator sdkModificator) {
        return null;
    }

}
