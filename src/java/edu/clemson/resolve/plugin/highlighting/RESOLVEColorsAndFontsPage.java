package edu.clemson.resolve.plugin.highlighting;

import edu.clemson.resolve.plugin.RESOLVEFileType;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

import static edu.clemson.resolve.plugin.highlighting.RESOLVESyntaxHighlightingColors.*;

public class RESOLVEColorsAndFontsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Comment", COMMENT),
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
            new AttributesDescriptor("Type specification", TYPE_SPECIFICATION),
            new AttributesDescriptor("Type reference", TYPE_REFERENCE),
            new AttributesDescriptor("Builtin type", BUILTIN_TYPE_REFERENCE),
            new AttributesDescriptor("Package exported interface", PACKAGE_EXPORTED_INTERFACE),
            new AttributesDescriptor("Package exported struct", PACKAGE_EXPORTED_STRUCT),
            new AttributesDescriptor("Package exported constant", PACKAGE_EXPORTED_CONSTANT),
            new AttributesDescriptor("Package exported variable", PACKAGE_EXPORTED_VARIABLE),
            new AttributesDescriptor("Package exported function", PACKAGE_EXPORTED_FUNCTION),
            new AttributesDescriptor("Package local interface", PACKAGE_LOCAL_INTERFACE),
            new AttributesDescriptor("Package local struct", PACKAGE_LOCAL_STRUCT),
            new AttributesDescriptor("Package local constant", PACKAGE_LOCAL_CONSTANT),
            new AttributesDescriptor("Package local variable", PACKAGE_LOCAL_VARIABLE),
            new AttributesDescriptor("Package local function", PACKAGE_LOCAL_FUNCTION),
            new AttributesDescriptor("Struct exported member", STRUCT_EXPORTED_MEMBER),
            new AttributesDescriptor("Struct exported method", STRUCT_EXPORTED_METHOD),
            new AttributesDescriptor("Struct local member", STRUCT_LOCAL_MEMBER),
            new AttributesDescriptor("Struct local method", STRUCT_LOCAL_METHOD),
            new AttributesDescriptor("Method receiver", METHOD_RECEIVER),
            new AttributesDescriptor("Function parameter", FUNCTION_PARAMETER),
            new AttributesDescriptor("Local constant", LOCAL_CONSTANT),
            new AttributesDescriptor("Local variable", LOCAL_VARIABLE),
            new AttributesDescriptor("Scope declared variable", SCOPE_VARIABLE),
    };
    private static final Map<String, TextAttributesKey> ATTRIBUTES_KEY_MAP = ContainerUtil.newTroveMap();
    static {
        ATTRIBUTES_KEY_MAP.put("tr", TYPE_REFERENCE);
        ATTRIBUTES_KEY_MAP.put("ts", TYPE_SPECIFICATION);
        ATTRIBUTES_KEY_MAP.put("bt", BUILTIN_TYPE_REFERENCE);
        ATTRIBUTES_KEY_MAP.put("kw", KEYWORD);
        ATTRIBUTES_KEY_MAP.put("pei", PACKAGE_EXPORTED_INTERFACE);
        ATTRIBUTES_KEY_MAP.put("pes", PACKAGE_EXPORTED_STRUCT);
        ATTRIBUTES_KEY_MAP.put("pec", PACKAGE_EXPORTED_CONSTANT);
        ATTRIBUTES_KEY_MAP.put("pev", PACKAGE_EXPORTED_VARIABLE);
        ATTRIBUTES_KEY_MAP.put("pef", PACKAGE_EXPORTED_FUNCTION);
        ATTRIBUTES_KEY_MAP.put("pli", PACKAGE_LOCAL_INTERFACE);
        ATTRIBUTES_KEY_MAP.put("pls", PACKAGE_LOCAL_STRUCT);
        ATTRIBUTES_KEY_MAP.put("plc", PACKAGE_LOCAL_CONSTANT);
        ATTRIBUTES_KEY_MAP.put("plv", PACKAGE_LOCAL_VARIABLE);
        ATTRIBUTES_KEY_MAP.put("plf", PACKAGE_LOCAL_FUNCTION);
        ATTRIBUTES_KEY_MAP.put("sem", STRUCT_EXPORTED_MEMBER);
        ATTRIBUTES_KEY_MAP.put("sef", STRUCT_EXPORTED_METHOD);
        ATTRIBUTES_KEY_MAP.put("slm", STRUCT_LOCAL_MEMBER);
        ATTRIBUTES_KEY_MAP.put("slf", STRUCT_LOCAL_METHOD);
        ATTRIBUTES_KEY_MAP.put("mr", METHOD_RECEIVER);
        ATTRIBUTES_KEY_MAP.put("fp", FUNCTION_PARAMETER);
        ATTRIBUTES_KEY_MAP.put("lc", LOCAL_CONSTANT);
        ATTRIBUTES_KEY_MAP.put("lv", LOCAL_VARIABLE);
        ATTRIBUTES_KEY_MAP.put("sv", SCOPE_VARIABLE);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return RESOLVEIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new RESOLVESyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return  "--Line Comment\n" +
                "(** Block comment *)\n" +
                "Facility Int_Swap_Example_Fac;\n" +
                "    uses Integer_Template;\n" +
                "\n" +
                "    Operation Exchange(updates I: Integer; updates J: Integer);\n" +
                "        ensures I = #J and J = #I;\n" +
                "    Procedure\n" +
                "        Var Temp: Integer;\n" +
                "\n" +
                "        Temp := I;\n" +
                "        I := J;\n" +
                "        J := Temp;\n" +
                "    end Exchange;\n" +
                "\n" +
                "    -- The operation below is intentionally buggy.\n" +
                "    Operation Exchange2(updates I: Integer; updates J: Integer);\n" +
                "        ensures I = #J and J = #I;\n" +
                "    Procedure\n" +
                "        I := I + J;\n" +
                "        J := I - J;\n" +
                "        I := I - J;\n" +
                "    end Exchange2;\n" +
                "\n" +
                "    (*\n" +
                "    Operation Main();\n" +
                "    Procedure\n" +
                "        Var X, Y: Integer;\n" +
                "        Write_Line(\"Input two numbers\");\n" +
                "        Read(X);\n" +
                "        Read(Y);\n" +
                "        Exchange(X, Y);\n" +
                "        Write(\"The first number is:\");\n" +
                "        Write(X);\n" +
                "        Write_Line(\"------------\"); \n" +
                "        Write(\"The second number is:\");\n" +
                "        Write(Y);\n" +
                "        Write_Line(\"------------\"); \n" +
                "        Exchange2(X, Y);\n" +
                "        Write(\"The first number is:\");\n" +
                "        Write(X);\n" +
                "        Write_Line(\"------------\"); \n" +
                "        Write(\"The second number is:\");\n" +
                "        Write(Y);\n" +
                "        Write_Line(\"------------\"); \n" +
                "    end Main; *)\n" +
                "end Int_Swap_Example_Fac;";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ATTRIBUTES_KEY_MAP;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return RESOLVEFileType.INSTANCE.getName();
    }
}
