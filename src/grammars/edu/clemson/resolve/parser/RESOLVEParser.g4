parser grammar RESOLVEParser;

options {
	tokenVocab=RESOLVELexer;
}

module
    :   precisModule
    ;

precisModule
    :   PRECIS SEMI
        END SEMI
    ;