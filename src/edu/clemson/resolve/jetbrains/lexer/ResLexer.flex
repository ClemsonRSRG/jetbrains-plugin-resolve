package edu.clemson.resolve.jetbrains.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.jetbrains.ResTypes;
import java.util.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static edu.clemson.resolve.jetbrains.RESOLVEParserDefinition.*;

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

NL = \R             //newline
WS = [ \t\f]        //whitespaces

LINE_COMMENT = "//" [^\r\n]*

LETTER = [:letter:] | "_"
DIGIT =  [:digit:]

INT_DIGIT = [0-9]
//TODO: Octal & hex..

NUM_INT = "0" | ([1-9] {INT_DIGIT}*)

IDENT = {LETTER} ({LETTER} | {DIGIT} )*

MBRACKET = ("∥"|"⟨"|"⟩"|"⎡"|"⎤"|"⎝"|"⎠"|"["|"]")
MSYM = ([\u2190-\u21FF] | [\u2100-\u214F] | [\u2200-\u22FF] | [\u27C0-\u27EF] | [\u27F0-\u27FF] | [\u2A00-\u2AFF] | [\u2300-\u23BF] | [\u0370-\u03FF])
SYM = ("!"|"*"|"+"|"-"|"/"|"|"|"~"|[<->])+

STR =      "\""
ESCAPES = [abfnrtv]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
"/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")? { return MULTILINE_COMMENT; }

// Punctuation

//TODO: Add new 'prime' symbol
"#"                                     { return POUND; }
"..."                                   { return TRIPLE_DOT; }
"."                                     { return DOT; }

"'" [^\\] "'"                           { return CHAR; }
"'" \n "'"                              { return CHAR; }
"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }
"'\\'"                                  { return BAD_CHARACTER; }

{STR} ( [^\"\\\n\r] | "\\" ("\\" | {STR} | {ESCAPES} | [0-8xuU] ) )* {STR}?
                                        { return STRING; }
// brackets & braces

"{{"                                    { return DBL_LBRACE; }
"{"                                     { return LBRACE; }
"}"                                     { return RBRACE; }
"}}"                                    { return DBL_RBRACE; }

"′"                                     { return PRIME; }
"|"                                     { return BAR; }
"("                                     { return LPAREN; }
")"                                     { return RPAREN; }
":"                                     { return COLON; }
"::"                                    { return COLON_COLON; }
";"                                     { return SEMICOLON; }
","                                     { return COMMA; }
"(i.)"                                  { return IND_BASE; }
"(ii.)"                                 { return IND_HYPO; }

// Operators

"λ"                                     { return LAMBDA; }
"="                                     { return EQUALS; }
":="                                    { return COLON_EQUALS; }
":=:"                                   { return COLON_EQUALS_COLON; }

// Keywords

"as"                                    { return AS; }
"by"                                    { return BY; }
"Cart_Prod"                             { return CART_PROD; }
"Categorical"                           { return CATEGORICAL; }
"changing"                              { return CHANGING; }
"Chainable"                             { return CHAINABLE; }
"Classification"                        { return CLASSIFICATION; }
"Concept"                               { return CONCEPT;  }
("constraints"|"Constraints")           { return CONSTRAINTS; }
"conventions"                           { return CONVENTIONS; }
"Corollary"                             { return COROLLARY; }
"correspondence"                        { return CORRESPONDENCE; }
"do"                                    { return DO; }
"decreasing"                            { return DECREASING; }
"Definition"                            { return DEFINITION; }
"Defines"                               { return DEFINES; }
"else"                                  { return ELSE; }
"Extension"                             { return EXTENSION; }
"Enhancement"                           { return ENHANCEMENT; }
"extended_by"                           { return EXTENDED_BY; }
"extended"                              { return EXTENDED; }
"end"                                   { return END;  }
"ensures"                               { return ENSURES; }
"exemplar"                              { return EXEMPLAR; }
"Exists"                                { return EXISTS; }
"∃"                                     { return EXISTS; }
"externally"                            { return EXTERNALLY; }
"Facility"                              { return FACILITY;  }
"false"                                 { return FALSE; }
"family"                                { return FAMILY; }
"Forall"                                { return FORALL; }
"∀"                                     { return FORALL; }
"for"                                   { return FOR; }
"from"                                  { return FROM; }
"if"                                    { return IF; }
"If"                                    { return PROG_IF; }
"is"                                    { return IS; }
"realized"                              { return REALIZED; }
"Realization"                           { return REALIZATION; }
"Implicit"                              { return IMPLICIT; }
"initialization"                        { return INITIALIZATION; }
"Inductive"                             { return INDUCTIVE; }
"lambda"                                { return LAMBDA; }
"maintaining"                           { return MAINTAINING; }
"modeled"                               { return MODELED; }
"Operation"                             { return OPERATION; }
"otherwise"                             { return OTHERWISE; }
"of"                                    { return OF; }
"Procedure"                             { return PROCEDURE; }
"Precis"                                { return PRECIS; }
"Recursive"                             { return RECURSIVE; }
"Record"                                { return RECORD; }
"requires"                              { return REQUIRES; }
"then"                                  { return THEN; }
"true"                                  { return TRUE; }
"Theorem"                               { return THEOREM; }
"Type"                                  { return FAMILY_TYPE; }
"type"                                  { return PARAM_TYPE; }
"uses"                                  { return USES; }
"Var"                                   { return VAR; }
"While"                                 { return WHILE; }
"with"                                  { return WITH; }
"which_entails"                         { return WHICH_ENTAILS; }

// Parameter modes

"alters"                                { return ALTERS; }
"updates"                               { return UPDATES; }
"clears"                                { return CLEARS; }
"restores"                              { return RESTORES; }
"preserves"                             { return PRESERVES; }
"replaces"                              { return REPLACES; }
"evaluates"                             { return EVALUATES; }

{MBRACKET}                              { return MATH_BRACKET_SYMBOL; }
{MSYM}                                  { return MATH_SYMBOL; }
{SYM}                                   { return SYMBOL; }
{IDENT}                                 { return IDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}