parser grammar Resolve;

options {
	tokenVocab=ResolveLexer;
}

module
    :   precisModule
    ;

precisModule
    :   PRECIS IDENTIFIER SEMI
        END IDENTIFIER SEMI EOF
    ;