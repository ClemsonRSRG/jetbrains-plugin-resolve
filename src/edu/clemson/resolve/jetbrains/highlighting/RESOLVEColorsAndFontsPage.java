package edu.clemson.resolve.jetbrains.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

import static edu.clemson.resolve.jetbrains.highlighting.RESOLVESyntaxHighlightingColors.*;

public class RESOLVEColorsAndFontsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Line comment", LINE_COMMENT),
            new AttributesDescriptor("Block comment", BLOCK_COMMENT),
            new AttributesDescriptor("Keyword", KEYWORD),
            new AttributesDescriptor("Identifier", IDENTIFIER),
            new AttributesDescriptor("String", STRING),
            new AttributesDescriptor("Number", NUMBER),
            new AttributesDescriptor("Semicolon", SEMICOLON),
            new AttributesDescriptor("Colon", COLON),
            new AttributesDescriptor("Comma", COMMA),
            new AttributesDescriptor("Dot", DOT),
            new AttributesDescriptor("Operator", OPERATOR),
            new AttributesDescriptor("Brackets", BRACKETS),
            new AttributesDescriptor("Braces", BRACES),
            new AttributesDescriptor("Parentheses", PARENTHESES),
            new AttributesDescriptor("Bad character", BAD_CHARACTER),
            new AttributesDescriptor("Type model", TYPE_MODEL),
            new AttributesDescriptor("Type reference", TYPE_REFERENCE),
            new AttributesDescriptor("Struct member", STRUCT_MEMBER),
            new AttributesDescriptor("Function parameter", FUNCTION_PARAMETER),
            new AttributesDescriptor("Parameter mode", PARAMETER_MODE),
    };

    private static final Map<String, TextAttributesKey> ATTRIBUTES_KEY_MAP =
            ContainerUtil.newTroveMap();

    @NotNull
    public String getDisplayName() {
        return RESOLVEFileType.INSTANCE.getName();
    }

    public Icon getIcon() {
        return RESOLVEIcons.MODULE;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    /*



    Facility Do_Nothing_Fac;
        uses Standard_Integers, Standard_Char_Strs;

    Operation Needlessly_Complicated_Do_Nothing (restores I : Integer);
    Procedure
    Var X : Integer;
    X := 1;
    X :=: I;

    //tell user what we're doing
    Write_Line("Doing nothing to X");
    Increment(X);
    Decrement(X);
    I :=: X;
    end Do_Nothing;
    end Do_Nothing_Fac;
     */
    @NotNull
    @Override
    public String getDemoText() {
        return
            "Facility Do_Nothing_Fac;\n" +
                    "        uses Standard_Integers, Standard_Char_Strs;\n\n" +
                    "    /* A needlessly complicated operation that does nothing */\n" +
                    "     Operation Do_Nothing (restores I : Integer);\n" +
                    "         Procedure\n" +
                    "             Var X : Std_Ints::Integer;\n" +
                    "             X := 1;\n" +
                    "             X :=: I;\n" +
                    "             //tell user what we're doing" +
                    "             Write_Line(\"Doing nothing to X\");\n" +
                    "             Increment(X);\n" +
                    "             Decrement(X);\n" +
                    "             I :=: X;\n" +
                    "     end Do_Nothing;\n" +
                    "end Do_Nothing_Fac;\n";

    }

    @NotNull
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ATTRIBUTES_KEY_MAP;
    }
}