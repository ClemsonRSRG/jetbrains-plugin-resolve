package edu.clemson.resolve.plugin.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class RESOLVEColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] ATTRIBUTES =
            {
                new AttributesDescriptor("Keywords", RESOLVESyntaxHighlighter.KEYWORD),
                new AttributesDescriptor("Numbers", RESOLVESyntaxHighlighter.NUMBER),
                new AttributesDescriptor("Parameter modes", RESOLVESyntaxHighlighter.PARAMETER_MODE),
                new AttributesDescriptor("Builtin operator", RESOLVESyntaxHighlighter.OPERATOR),
                new AttributesDescriptor("String literal", RESOLVESyntaxHighlighter.STRING),
                new AttributesDescriptor("Line comments", RESOLVESyntaxHighlighter.LINE_COMMENT),
                new AttributesDescriptor("Doc comments", RESOLVESyntaxHighlighter.JAVADOC_COMMENT),
                new AttributesDescriptor("Block comments", RESOLVESyntaxHighlighter.BLOCK_COMMENT),
            };

    @Nullable @Override public Icon getIcon() {
        return RESOLVEIcons.MODULE;
    }

    @NotNull @Override public SyntaxHighlighter getHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    @NotNull @Override public String getDemoText() {
        return
                "Facility Foo;\n" +
                        "        uses Far, Fizz;\n\n" +
                        "    /**\n" +
                        "     * A doc-style comment.\n" +
                        "     */\n" +
                        "     Facility F is T<G, U>(5, 10) implemented by T_I;\n\n" +
                        "     // the quick brown fox jumps of the lazy dog\n" +
                        "     Operation Boo (evaluates I : Integer);\n" +
                        "         Procedure\n" +
                        "             Var x : Integer;\n" +
                        "             Var y, z : Std_String_Fac :: String;\n" +
                        "             y:=\"cat\"; z =:=\"dog\";\n" +
                        "             z:= y++z;\n" +
                        "             Zoo(x);\n" +
                        "     end Boo;\n" +
                        "     Operation Zoo (alters p : Integer);\n" +
                        "         Procedure\n" +
                        "             p := p + 1;" +
                        "     end;\n" +
                        "end Foo;\n";
    }

    @Nullable @Override public Map<String, TextAttributesKey>
            getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull @Override public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRIBUTES;
    }

    @NotNull @Override public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull @Override public String getDisplayName() {
        return "RESOLVE";
    }
}
