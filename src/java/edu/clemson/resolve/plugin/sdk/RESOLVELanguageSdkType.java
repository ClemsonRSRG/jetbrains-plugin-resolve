package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.util.SystemInfo;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

public class RESOLVELanguageSdkType extends SdkType {

    public static final String RESOLVE_LANGUAGE_SDK_TYPE_ID = "RESOLVE Language SDK";

    public static final File DEFAULT_SDK_PATH_WINDOWS = new File("c:/resolve/");
    public static final File DEFAULT_SDK_PATH_OSX = new File("/usr/local/resolve");
    public static final File DEFAULT_SDK_PATH_LINUX = new File("/usr/");

    // Messages go to the log available in Help -> Show log in finder.
    private final static Logger LOG = Logger.getInstance(RESOLVELanguageSdkType.class);

    public RESOLVELanguageSdkType() {
        super(RESOLVE_LANGUAGE_SDK_TYPE_ID);
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
        return RESOLVEIcons.MODULE;
    }

    @Nullable @Override public String suggestHomePath() {
        if (SystemInfo.isWindows) {
            if (DEFAULT_SDK_PATH_WINDOWS.exists()) {
                return DEFAULT_SDK_PATH_WINDOWS.getAbsolutePath();
            }
        } else if (SystemInfo.isMac) {
            if (DEFAULT_SDK_PATH_OSX.exists()) {
                return DEFAULT_SDK_PATH_OSX.getAbsolutePath();
            }
        } else if (SystemInfo.isLinux) {
            if (DEFAULT_SDK_PATH_LINUX.exists()) {
                return DEFAULT_SDK_PATH_LINUX.getAbsolutePath();
            }
        }
        return null;
    }

    @Override public boolean isValidSdkHome(String path) {
       /* File resolve = getCompilerExecutable(path);
        resolve.setExecutable(true);
        boolean result = resolve.canExecute();*/
        return true;
    }

    @Override public String suggestSdkName(String currentSdkName,
                                       String sdkHome) {
        return "RESOLVE";
    }

    @Nullable @Override public AdditionalDataConfigurable
    createAdditionalDataConfigurable(
            SdkModel sdkModel, SdkModificator sdkModificator) {
        return null;
    }

    @Override public String getPresentableName() {
        return RESOLVE_LANGUAGE_SDK_TYPE_ID;
    }

    @Override public void saveAdditionalData(
            @NotNull SdkAdditionalData additionalData,
            @NotNull Element additional) {
    }

    /**
     * Gets a file reference to compiler executable
     *
     * @param SDKPath path to SDK
     * @return File reference to compiler executable
     */
    public static File getCompilerExecutable(@NotNull String SDKPath) {
        File SDKfolder = new File(SDKPath);
        File binaryFolder = new File(SDKfolder, "bin");
        return new File(binaryFolder, "resolve-0.0.1-SNAPSHOT-jar-with-dependencies");
    }
}