/*
 * [The "BSD license"]
 * Copyright (c) 2015 Clemson University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. The name of the author may not be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
lexer grammar ResolveLexer;

DOC_COMMENT
	:	'/**' .*? ('*/' | EOF) -> channel(HIDDEN)
	;

BLOCK_COMMENT
	:	'/*' .*? ('*/' | EOF)  -> channel(HIDDEN)
	;

LINE_COMMENT
	:	'//' ~[\r\n]*  -> channel(HIDDEN)
	;

// keywords

AFFECTS			:	'affects'			;
PRECIS			:   'Precis'			;
BY				:	'by'				;
CART_PROD		:	'Cart_Prod'			;
CATEGORICAL		:	'Categorical'		;
CONCEPT			:	'Concept'			;
CONSTRAINT		:	('constraints'|'Constraints')		;
CONVENTION		:	'convention'		;
COROLLARY		:	'Corollary'			;
CORRESPONDENCE 	:	'correspondence'	;
DEFINITION		:	'Definition'		;
DEFINES			:	'Defines'			;
DEPENDENT		:	'Dependent_Terms'	;
DECREASING		:	'decreasing'		;
DO 				:	'do'				;
ELSE			:	'else'				;
EXTENDED		:	'extended'			;
END         	:   'end'				;
ENSURES			:	'ensures'			;
ENTAILS			:	'which_entails'		;
EXEMPLAR		:	'exemplar'			;
EXISTS			:	'Exists' ;
EXTERNALLY		:	'externally'		;
EXTENSION		:	'Extension'			;
FACILITY		:	'Facility'			;
FACILITY_INIT	:	'Facility_Initialization' ;
FALSE			:	'false'				;
FAMILY			:	'Family'			;
FOR				:	'for'				;
FORALL			:	'Forall'			;
IF				:	('if'|'If')			;
IFF				:	'iff'				;
IMPLICIT		:	'Implicit'			;
INIT			:	'initialization'	;
IS 				:	'is'				;
IMPLEMENTATION	:	'Implementation'	;
IMPLEMENTED		:	'implemented'		;
INDUCTIVE		:	'Inductive'			;
LAMBDA			:	'lambda'			;
MAINTAINING		:	'maintaining'		;
MODELED			:	'modeled'			;
NOT 			:	'not'				;
OF				:	'of'				;
ON 				:	'on'				;
OTHERWISE		:	'otherwise'			;
OPERATION		:	'Operation'			;
PROCEDURE		:	'Procedure'			;
RECORD			:	'Record'			;
RECURSIVE		:	'Recursive'			;
REQUIRES		:	'requires'			;
THEOREM			:	'Theorem'			;
THEN            :   'then'              ;
TRUE			:	'true'				;
TYPE			:	'Type'				;
USES			:	'uses'				;
VAR				:	'Var'				;
WHILE			:	'While'				;
WITH			:	'with'				;

// parameter modes

ALTERS			:	'alters'		;
UPDATES			:	'updates'		;
CLEARS			:	'clears'		;
RESTORES		:	'restores'		;
PRESERVES		:	'preserves'		;
REPLACES		:	'replaces'		;
EVALUATES		:	'evaluates'		;

// punctuation

AT				:	'@'				;
BASE_CASE		:	'(i.)'			;
COLON			:	':'				;
COLONCOLON		:	'::'			;
COMMA			:	','				;
DOT 			:	'.'				;
DBL_RBRACE		:	'}}'			;
DBL_LBRACE		:	'{{'			;
INDUCTIVE_CASE	:	'(ii.)'			;
LBRACE 			:	'{'				;
RBRACE  		:	'}'				;
LBRACKET		:	'['				;
LPAREN			:	'('				;
RPAREN			:	')'				;
RBRACKET		:	']'				;
SEMI			:	';'				;

// operators

AND				:	'and'			;
ASSIGN			:	':='			;
BAR				:	'|'				;
CAT				:	'o'				;
DBL_BAR			:	'||'			;
DIVIDE			:	'/'				;
LDIVIDE			:	'\\'			;
EQUALS			:	'='				;
GT				:	'>'				;
GTE				:	'>='			;
IMPLIES			:	'implies'		;
INTERSECT		:	'intersect'		;
IS_IN			:	'is_in'			;
IS_NOT_IN		:	'is_not_in'		;
LT 				:	'<'				;
LTE				:	'<='			;
MINUS			:	'-'				;
MINUSMINUS		:	'--'			;
MOD				:	'%'				;
MULT			:	'*'				;
NEQUALS			:	'/='			;
OR 				:	'or'			;
PLUS			:	'+'				;
PLUSPLUS		:	'++'			;
RANGE			:	'..'			;
RARROW			:	'->'			;
SWAP			:	':=:'			;
TILDE			:	'~'				;
TRIPLEDOT		:	'...'			;
UNION			:	'union'			;

ID	:	NameStartChar NameChar*;

fragment
NameChar
	:   NameStartChar
	|   '0'..'9'
	|   '_'
	;

fragment
NameStartChar
	:   'A'..'Z'
	|   'a'..'z'
	;

INT	: [0-9]+
	;

CHAR: '\'' . '\'' ;

STRING: '"' ~( '"' | '\r' | '\n' )* '"';

PROG_BOOL
    :   TRUE
    |   FALSE
    ;

WS  :	[ \t\r\n\f]+ -> channel(HIDDEN)	;

// -----------------
// Illegal Character
//
// This is an illegal character trap which is always the last rule in the
// lexer specification. It matches a single character of any value and being
// the last rule in the file will match when no other rule knows what to do
// about the character. It is reported as an error but is not passed on to the
// parser. This means that the parser to deal with the gramamr file anyway
// but we will not try to analyse or code generate from a file with lexical
// errors.
//
ERRCHAR
	:	.	-> channel(HIDDEN)
	;
