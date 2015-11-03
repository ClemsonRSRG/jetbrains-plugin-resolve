package edu.clemson.resolve.plugin.template;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RESOLVELiveTemplatesProvider
        implements
            DefaultLiveTemplatesProvider {

    @NotNull @Override public String[] getDefaultLiveTemplateFiles() {
        return new String[]{"/liveTemplates/resolve"};
    }

    @Nullable @Override public String[] getHiddenLiveTemplateFiles() {
        return new String[]{"/liveTemplates/resolveHidden"};
    }
}
