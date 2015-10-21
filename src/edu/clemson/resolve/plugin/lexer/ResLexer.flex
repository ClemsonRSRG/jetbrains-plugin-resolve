package edu.clemson.resolve.plugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.ResTypes;
import java.util.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static edu.clemson.resolve.plugin.RESOLVEParserDefinition.*;

%%

%{
  public _ResLexer() {
    this((java.io.Reader)null);
  }
%}

%unicode
%class _ResLexer
%implements FlexLexer, ResTypes
%function advance
%type IElementType

%eof{
  return;
%eof}

NL = [\r\n] | \r\n      // NewLine
WS = [ \t\f]            // Whitespaces

LINE_COMMENT = "//" [^\r\n]*

LETTER = [:letter:] | "_"
DIGIT =  [:digit:]

INT_DIGIT = [0-9]
//TODO: Octal & hex..

NUM_INT = "0" | ([1-9] {INT_DIGIT}*)

IDENT = {LETTER} ({LETTER} | {DIGIT} )*
STR =      "\""
ESCAPES = [abfnrtv]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
"/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")? { return MULTILINE_COMMENT; }

"..."                                   { return TRIPLE_DOT; }
"."                                     { return DOT; }

"'" [^\\] "'"                           { return CHAR; }
"'" \n "'"                              { return CHAR; }
"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }
"'\\'"                                  { return BAD_CHARACTER; }


"`" [^`]* "`"?                          { return RAW_STRING; }
{STR} ( [^\"\\\n\r] | "\\" ("\\" | {STR} | {ESCAPES} | [0-8xuU] ) )* {STR}?
                                        { return STRING; }

"{"                                     { return LBRACE; }
"}"                                     { return RBRACE; }

"["                                     { return LBRACK; }
"]"                                     { return RBRACK; }

"("                                     { return LPAREN; }
")"                                     { return RPAREN; }

":"                                     { return COLON; }
";"                                     { return SEMICOLON; }
","                                     { return COMMA; }

"="                                     { return EQUALS; }

"by"                                    { return BY; }
"Concept"                               { return CONCEPT;  }
"end"                                   { return END;  }
"exemplar"                              { return EXEMPLAR; }
"externally"                            { return EXTERNALLY; }
"Facility"                              { return FACILITY;  }
"Family"                                { return FAMILY; }
"is"                                    { return IS; }
"implemented"                           { return IMPLEMENTED; }
"modeled"                               { return MODELED; }
"Operation"                             { return OPERATION; }
"Procedure"                             { return PROCEDURE; }
"Recursive"                             { return RECURSIVE; }
"Record"                                { return RECORD; }
"Type"                                  { return TYPE; }
"uses"                                  { return USES; }
"Var"                                   { return VAR; }

{IDENT}                                 { return IDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}