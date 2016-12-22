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

%class _ResLexer
%implements FlexLexer, ResTypes
%unicode

%function advance
%type IElementType

%eof{
  return;
%eof}

NL = \R             //newline
WS = [ \t\f]        //whitespaces

LINE_COMMENT = "//" [^\r\n]*
MULTILINE_COMMENT = "/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?

LETTER = [:letter:] | "_"
DIGIT =  [:digit:]

INT_DIGIT = [0-9]
NUM_INT = "0" | ([1-9] {INT_DIGIT}*)

IDENT = {LETTER} ({LETTER} | {DIGIT} )*

MSYM = ({U_ARROW} | {U_LOGIC} | {U_LETTER} | {U_OPERATOR}  | {U_RELATION} | [\u0370-\u03FF])

U_ARROW     = ("←"|"⇐"|"⟵"|"⟸"|"→"|"⇒"|"⟶"|"⟹"|"↔"|"⇔"|"⟷"|
               "⟺"|"↩"|"↪"|"↽"|"⇁"|"↼"|"⇀"|"⇌"|"↝"|"⇃"|"⇂"|"↿"|"↾"|
               "↑"|"⇑"|"↓"|"⇓"|"↕"|"⇕"|"↤"|"↦"|"↢"|"↣")

U_LOGIC     = ("∧"|"⋀"|"∨"|"⋁"|"¬"|"⋄")
U_LETTER    = ("ℂ"|"ℕ"|"ℚ"|"ℝ"|"ℤ"|"℘")

U_OPERATOR  = ("∩"|"⋂"|"∪"|"⋃"|"⊔"|"⨆"|"⊓"|"⨅"|"∝"|"⊎"|"⨄"|"±"|"∓"|"×"|"÷"|
               "⋅"|"⋆"|"∙"|"∘"|"⊕"|"⨁"|"⊗"|"⨂"|"⊙"|"⨀"|"⊖"|"⊘"|"⟕"|"⟖"|"⟗"|
               "∑"|"∏"|"⨿"|"∐"|"⋈"|"⋉"|"⋊"|"⊠"|"⊡"|"∎"|"⨪")

U_RELATION  = ("⊢"|"⊨"|"⊩"|"⊫"|"⊣"|"≤"|"≥"|"≪"|"≫"|"≲"|"≳"|"⪅"|"⪆"|"∈"|
               "∉"|"⊂"|"⊃"|"⊆"|"⊇"|"⊏"|"⊐"|"⊑"|"⊒"|"∼"|"≐"|"≃"|"≈"|"≍"|"≠"|
               "≅"|"≡"|"≼"|"≽"|"⊲"|"⊳"|"⊴"|"⊵"|"△"|"≜")

//if we allow '|' in here, then math outfix exprs need to be | |x| o b| (space between the |x| and the leftmost
SYM = ("!"|"*"|"+"|"-"|"/"|"~"|"<"|"="|">")
STR =      "\""
STRING = {STR} ( [^\"\\\n\r] | "\\" ("\\" | {STR} | {ESCAPES} | [0-8xuU] ) )* {STR}?
ESCAPES = [abfnrtv]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
{MULTILINE_COMMENT}                     { return MULTILINE_COMMENT; }
{STRING}                                { return STRING; }

"'\\'"                                  { return BAD_CHARACTER; }
"'" [^\\] "'"                           { return CHAR; }
"'" \n "'"                              { return CHAR; }
"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }

"#"                                     { return POUND; }
"..."                                   { return TRIPLE_DOT; }
"."                                     { return DOT; }

// brackets & braces

"∥"                                     { return DBL_BAR; }

"⟨"                                     { return LANGLE; }
"⟩"                                     { return RANGLE; }

"⌈"                                     { return LCEIL; }
"⌉"                                     { return RCEIL; }

"⎝"                                     { return LCUP; }
"⎠"                                     { return RCUP; }

"["                                     { return LBRACK; }
"]"                                     { return RBRACK; }

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
":="                                    { return COLON_EQUALS; }
":=:"                                   { return COLON_EQUALS_COLON; }

"∃"                                     { return EXISTS; }
"∀"                                     { return FORALL; }

// Keywords

"by"                                    { return BY; }
"Cart_Prod"                             { return CART_PROD; }
"Categorical"                           { return CATEGORICAL; }
"changing"                              { return CHANGING; }
"Chainable"                             { return CHAINABLE; }
"Concept"                               { return CONCEPT;  }
"constraints"                           { return CONSTRAINTS; }
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
"enhanced"                              { return ENHANCED; }
"end"                                   { return END;  }
"ensures"                               { return ENSURES; }
"exemplar"                              { return EXEMPLAR; }

"externally"                            { return EXTERNALLY; }
"Facility"                              { return FACILITY;  }
"false"                                 { return FALSE; }
"family"                                { return FAMILY; }
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
"maintaining"                           { return MAINTAINING; }
"modeled"                               { return MODELED; }
"Notice"                                { return NOTICE; }
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
"which_entails"                         { return WHICH_ENTAILS; }

// Parameter modes

"alters"                                { return ALTERS; }
"updates"                               { return UPDATES; }
"clears"                                { return CLEARS; }
"restores"                              { return RESTORES; }
"preserves"                             { return PRESERVES; }
"replaces"                              { return REPLACES; }
"evaluates"                             { return EVALUATES; }

{MSYM}                                  { return MATH_SYMBOL; }
{SYM}                                   { return SYMBOL; }
{IDENT}                                 { return IDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}