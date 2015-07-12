package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
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
        VirtualFile suggestSdkDirectory = RESOLVESdkUtil.suggestSdkDirectory();
        return suggestSdkDirectory != null ? suggestSdkDirectory.getPath() : null;
    }

    @Override public boolean isValidSdkHome(String path) {
        File resolve = RESOLVESdkUtil.getCompilerExecutable(path);
        if (resolve == null) {
            return false;
        }
        return resolve.exists();
    }

    @Override public String suggestSdkName(
            String currentSdkName, String sdkHome) {
        return "RESOLVE";
    }

    @Nullable @Override public String getVersionString(
            @NotNull String sdkHome) {
        File f = RESOLVESdkUtil.getCompilerExecutable(sdkHome);
        if (f == null) {
            return null;
        }
        String name = f.getName();
        return name.substring(name.indexOf('-',0)+1, name.indexOf('-',0)+6);
    }

    @Nullable @Override public AdditionalDataConfigurable
    createAdditionalDataConfigurable(
            SdkModel sdkModel, SdkModificator sdkModificator) {
        return null;
    }

    @Override public void saveAdditionalData(
            @NotNull SdkAdditionalData additionalData,
            @NotNull Element additional) {
    }

    @Override public String getPresentableName() {
        return RESOLVE_LANGUAGE_SDK_TYPE_ID;
    }

    @Override
    public void setupSdkPaths(@NotNull Sdk sdk) {
        String versionString = sdk.getVersionString();
        if (versionString == null) throw new RuntimeException("SDK version is not defined");
        SdkModificator modificator = sdk.getSdkModificator();
        String path = sdk.getHomePath();
        if (path == null) return;
        modificator.setHomePath(path);

        for (VirtualFile file : RESOLVESdkUtil
                .getSdkDirectoriesToAttach(path, versionString)) {
            modificator.addRoot(file, OrderRootType.CLASSES);
            modificator.addRoot(file, OrderRootType.SOURCES);
        }
        modificator.commitChanges();
    }


}