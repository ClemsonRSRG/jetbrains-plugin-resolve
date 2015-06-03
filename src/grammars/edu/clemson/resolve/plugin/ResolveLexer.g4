lexer grammar ResolveLexer;

PRECIS      :   'Precis'                        ;
END         :   'end'                           ;
SEMI        :   ';'                             ;
IDENTIFIER	:	NameStartChar NameChar*         ;
WS          :	[ \t\r\n\f]+ -> channel(HIDDEN)	;

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