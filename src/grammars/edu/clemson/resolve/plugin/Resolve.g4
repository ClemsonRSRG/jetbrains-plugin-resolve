parser grammar Resolve;

options {
	tokenVocab=ResolveLexer;
}

module
    :   precisModule
    ;

usesList
    :   USES IDENTIFIER (COMMA IDENTIFIER)* SEMI
    ;

precisModule
    :   PRECIS IDENTIFIER SEMI
        (usesList)?
        END IDENTIFIER SEMI EOF
    ;