lexer grammar ResolveLexer;

PRECIS      :   'Precis'                        ;
FACILITY	:	'Facility'						;
END         :   'end'                           ;
USES		:	'uses'							;

SEMI        :   ';'                             ;
COMMA		:	','								;

IDENTIFIER	:	NameStartChar NameChar*         ;
WS          :	[ \t\r\n\f]+ -> channel(HIDDEN)	;

DOC_COMMENT
	:	'(**' .*? ('*)' | EOF)
	;

BLOCK_COMMENT
	:	'(*' .*? ('*)' | EOF)  -> channel(HIDDEN)
    ;

LINE_COMMENT
	:	'--' ~[\r\n]*  -> channel(HIDDEN)
	;

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
