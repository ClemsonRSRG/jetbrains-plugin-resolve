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

public class RESOLVESdkType extends SdkType {

    public static final String RESOLVE_LANGUAGE_SDK_TYPE_ID = "RESOLVE Language SDK";

    public RESOLVESdkType() {
        super(RESOLVE_LANGUAGE_SDK_TYPE_ID);
    }

    @NotNull public static RESOLVESdkType getInstance() {
        final RESOLVESdkType instance =
                SdkType.findInstance(RESOLVESdkType.class);
        assert instance != null;
        return instance;
    }

    @NotNull @Override public Icon getIcon() {
        return RESOLVEIcons.MODULE;
    }

    @NotNull @Override public Icon getIconForAddAction() {
        return getIcon();
    }

    @Nullable @Override public String suggestHomePath() {
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