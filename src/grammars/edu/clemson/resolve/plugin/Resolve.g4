parser grammar Resolve;

options {
	tokenVocab=ResolveLexer;
}

module
    :   precisModule
    |   facilityModule
    ;

usesList
    :   USES IDENTIFIER (COMMA IDENTIFIER)* SEMI
    ;

facilityModule
    :   FACILITY IDENTIFIER SEMI
        (usesList)?
        END IDENTIFIER SEMI EOF
    ;

precisModule
    :   PRECIS IDENTIFIER SEMI
        (usesList)?
        END IDENTIFIER SEMI EOF
    ;