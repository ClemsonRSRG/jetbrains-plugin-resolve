package edu.clemson.resolve.jetbrains.sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.jetbrains.RESOLVEConstants;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVESdkType extends SdkType {

    public RESOLVESdkType() {
        super(RESOLVEConstants.SDK_TYPE_ID);
    }

    @NotNull
    public static RESOLVESdkType getInstance() {
        return SdkType.findInstance(RESOLVESdkType.class);
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return RESOLVEIcons.TOOL_ICON;
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return getIcon();
    }

    @Nullable
    @Override
    public String suggestHomePath() {
        VirtualFile suggestSdkDirectory = RESOLVESdkUtil.suggestSdkDirectory();
        return suggestSdkDirectory != null ?
                suggestSdkDirectory.getPath() : null;
    }

    @Override
    public boolean isValidSdkHome(@NotNull String sdkHomePath) {
        RESOLVESdkService.LOG.debug("Validating sdk path: " + sdkHomePath);
        //note that we don't explicitly validate the resolve compiler jar
        //exists here--no need, as this will return null if it doesn't
        // (the version is embedded in it)
        if (getVersionString(sdkHomePath) == null) {
            RESOLVESdkService.LOG.debug("Cannot retrieve version for sdk " +
                    "(or the compiler-jar): " + sdkHomePath);
            return false;
        }
        return true;
    }

    @Override
    public String suggestSdkName(String currentSdkName,
                                 String sdkHome) {
        String version = getVersionString(sdkHome);
        if (version == null) {
            return "Unknown RESOLVE version at " + sdkHome;
        }
        return "RESOLVE " + version;
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull String sdkHome) {
        return RESOLVESdkUtil.retrieveRESOLVEVersion(sdkHome);
    }

    @Nullable
    @Override
    public String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
        return null;
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable
    createAdditionalDataConfigurable(
            @NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
        return null;
    }

    @Override
    public void saveAdditionalData(
            @NotNull SdkAdditionalData additionalData,
            @NotNull Element additional) {
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "RESOLVE SDK";
    }

    @Override
    public void setupSdkPaths(@NotNull Sdk sdk) {
        String versionString = sdk.getVersionString();
        if (versionString == null) {
            throw new RuntimeException("SDK version is not defined");
        }
        SdkModificator modificator = sdk.getSdkModificator();
        String path = sdk.getHomePath();
        if (path == null) return;
        modificator.setHomePath(path);

        for (VirtualFile file : RESOLVESdkUtil.getSdkDirectoriesToAttach(path, versionString)) {
            modificator.addRoot(file, OrderRootType.CLASSES);
            modificator.addRoot(file, OrderRootType.SOURCES);
        }
        modificator.commitChanges();
    }
}