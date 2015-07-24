package edu.clemson.resolve.plugin.highlighting;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RESOLVESyntaxHighlightingColors {

    public static final TextAttributesKey COMMENT = createTextAttributesKey("RESOLVE_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("RESOLVE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = createTextAttributesKey("RESOLVE_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("RESOLVE_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("RESOLVE_BRACKET", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey BRACES = createTextAttributesKey("RESOLVE_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("RESOLVE_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey OPERATOR = createTextAttributesKey("RESOLVE_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("RESOLVE_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey DOT = createTextAttributesKey("RESOLVE_DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("RESOLVE_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COLON = createTextAttributesKey("RESOLVE_COLON", HighlighterColors.TEXT);
    public static final TextAttributesKey COMMA = createTextAttributesKey("RESOLVE_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("RESOLVE_BAD_TOKEN", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey TYPE_SPECIFICATION = createTextAttributesKey("RESOLVE_TYPE_SPECIFICATION", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey TYPE_REFERENCE = createTextAttributesKey("RESOLVE_TYPE_REFERENCE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    public static final TextAttributesKey BUILTIN_TYPE_REFERENCE = createTextAttributesKey("RESOLVE_BUILTIN_TYPE_REFERENCE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    public static final TextAttributesKey PACKAGE_EXPORTED_INTERFACE = createTextAttributesKey("RESOLVE_PACKAGE_EXPORTED_INTERFACE", DefaultLanguageHighlighterColors.INTERFACE_NAME);
    public static final TextAttributesKey PACKAGE_EXPORTED_STRUCT = createTextAttributesKey("RESOLVE_PACKAGE_EXPORTED_STRUCT", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey PACKAGE_EXPORTED_CONSTANT = createTextAttributesKey("RESOLVE_PACKAGE_EXPORTED_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey PACKAGE_EXPORTED_VARIABLE = createTextAttributesKey("RESOLVE_PACKAGE_EXPORTED_VARIABLE", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
    public static final TextAttributesKey PACKAGE_EXPORTED_FUNCTION = createTextAttributesKey("RESOLVE_EXPORTED_FUNCTION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey PACKAGE_LOCAL_INTERFACE = createTextAttributesKey("RESOLVE_PACKAGE_LOCAL_INTERFACE", DefaultLanguageHighlighterColors.INTERFACE_NAME);
    public static final TextAttributesKey PACKAGE_LOCAL_STRUCT = createTextAttributesKey("RESOLVE_PACKAGE_LOCAL_STRUCT", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey PACKAGE_LOCAL_CONSTANT = createTextAttributesKey("RESOLVE_PACKAGE_LOCAL_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey PACKAGE_LOCAL_VARIABLE = createTextAttributesKey("RESOLVE_PACKAGE_LOCAL_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey PACKAGE_LOCAL_FUNCTION = createTextAttributesKey("RESOLVE_LOCAL_FUNCTION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey STRUCT_EXPORTED_MEMBER = createTextAttributesKey("RESOLVE_STRUCT_EXPORTED_MEMBER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
    public static final TextAttributesKey STRUCT_EXPORTED_METHOD = createTextAttributesKey("RESOLVE_STRUCT_EXPORTED_METHOD", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey STRUCT_LOCAL_MEMBER = createTextAttributesKey("RESOLVE_STRUCT_LOCAL_MEMBER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey STRUCT_LOCAL_METHOD = createTextAttributesKey("RESOLVE_STRUCT_LOCAL_METHOD", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey METHOD_RECEIVER = createTextAttributesKey("RESOLVE_METHOD_RECEIVER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey FUNCTION_PARAMETER = createTextAttributesKey("RESOLVE_FUNCTION_PARAMETER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey LOCAL_CONSTANT = createTextAttributesKey("RESOLVE_LOCAL_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey LOCAL_VARIABLE = createTextAttributesKey("RESOLVE_LOCAL_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey SCOPE_VARIABLE = createTextAttributesKey("RESOLVE_SCOPE_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    private RESOLVESyntaxHighlightingColors() {
    }
}
