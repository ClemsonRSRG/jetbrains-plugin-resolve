package edu.clemson.resolve.plugin.configdialogs;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import edu.clemson.resolve.plugin.Icons;
import edu.clemson.resolve.plugin.RESOLVESyntaxHighlighter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class RESOLVEColorsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] ATTRIBUTES =
            {
               //     new AttributesDescriptor("Lexer Rule", RESOLVESyntaxHighlighter.TOKENNAME),
               //     new AttributesDescriptor("Parser Rule", ANTLRv4SyntaxHighlighter.RULENAME),
            };

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "Concept Stack_Template (type Entry; " +
                        "evaluates Max_Depth: Integer);\n\t" +
                        "uses String_Theory, Standard_Integers;";

    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRIBUTES;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "RESOLVE";
    }
}
