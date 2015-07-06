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
parser grammar Resolve;

options {
	tokenVocab=ResolveLexer;
}

module
    :   precisModule
    |   conceptModule
    |   conceptImplModule
    |   facilityModule
    ;

conceptModule
    :   CONCEPT name=ID SEMI
        (usesList)?
        (requiresClause)?
        conceptBlock
        END closename=ID SEMI EOF
    ;

conceptBlock
    :   ( typeModelDecl
        | operationDecl
        )*
    ;

// implementation modules

conceptImplModule
    :   IMPL name=ID FOR concept=ID SEMI
        (usesList)?
        (requiresClause)?
        END closename=ID SEMI EOF
    ;

// facility modules

facilityModule
    :   FACILITY name=ID SEMI
        (usesList)?
        (requiresClause)?
        facilityBlock
        END closename=ID SEMI EOF
    ;

facilityBlock
    :   ( operationProcedureDecl
        )*
    ;

precisModule
    :   PRECIS name=ID SEMI
        (usesList)?
        END closename=ID SEMI EOF
    ;

// uses, imports

usesList
    :   USES ID (COMMA ID)* SEMI
    ;

// parameter and parameter-list related rules

operationParameterList
    :   LPAREN (parameterDeclGroup (SEMI parameterDeclGroup)*)?  RPAREN
    ;

parameterDeclGroup
    :   parameterMode ID (COMMA ID)* COLON type
    ;

parameterMode
    :   ( ALTERS
        | UPDATES
        | CLEARS
        | RESTORES
        | PRESERVES
        | REPLACES
        | EVALUATES )
    ;

// type and record related rules

type
    :   (qualifier=ID COLONCOLON)? name=ID
    ;

typeModelDecl
    :   TYPE FAMILY name=ID IS MODELED BY mathTypeExp SEMI
        EXEMPLAR exemplar=ID SEMI
        (constraintClause)?
        (typeModelInit)?
    ;

// type initialization rules

typeModelInit
    :   INITIALIZATION (ensuresClause)?
    ;

// functions

operationDecl
    :   OPERATION name=ID operationParameterList (COLON type)? SEMI
        (requiresClause)? (ensuresClause)?
    ;

operationProcedureDecl
    :   (recursive=RECURSIVE)? OPERATION
        name=ID operationParameterList (COLON type)? SEMI
        (requiresClause)?
        (ensuresClause)?
        PROCEDURE
       // (variableDeclGroup)*
       // (stmt)*
        END closename=ID SEMI
    ;

mathVariableDeclGroup
    :   ID (COMMA ID)* COLON mathTypeExp
    ;

mathVariableDecl
    :   ID COLON mathTypeExp
    ;

// mathematical clauses

requiresClause
    :   REQUIRES mathAssertionExp (entailsClause)? SEMI
    ;

ensuresClause
    :   ENSURES mathAssertionExp SEMI
    ;

constraintClause
    :   CONSTRAINT mathAssertionExp SEMI
    ;

conventionClause
    :   CONVENTION mathAssertionExp (entailsClause)? SEMI
    ;

correspondenceClause
    :   CORRESPONDENCE mathAssertionExp SEMI
    ;

entailsClause
    :   ENTAILS mathExp (COMMA mathExp)* COLON mathTypeExp
    ;

// mathematical expressions

mathTypeExp
    :   mathExp
    ;

mathAssertionExp
    :   mathExp
    |   mathQuantifiedExp
    ;

mathQuantifiedExp
    :   q=(FORALL|EXISTS) mathVariableDeclGroup COMMA mathAssertionExp
    ;

mathExp
    :   mathPrimaryExp                                  #mathPrimeExp
    |   mathExp op=(MULT|DIVIDE|TILDE) mathExp          #mathInfixExp
    |   mathExp op=(PLUS|MINUS|CUTMINUS) mathExp        #mathInfixExp
    |   mathExp op=(RANGE|RARROW) mathExp               #mathInfixExp
    |   mathExp op=(CAT|UNION|INTERSECT) mathExp        #mathInfixExp
    |   mathExp op=(IS_IN|IS_NOT_IN) mathExp            #mathInfixExp
    |   mathExp op=(LTE|GTE|GT|LT) mathExp              #mathInfixExp
    |   mathExp op=(EQUALS|NEQUALS) mathExp             #mathInfixExp
    |   mathExp op=IMPLIES mathExp                      #mathInfixExp
    |   mathExp op=(AND|OR) mathExp                     #mathInfixExp
    |   mathExp op=COLON mathTypeExp                    #mathTypeAssertionExp
    |   LPAREN mathAssertionExp RPAREN                  #mathNestedExp
    ;

mathPrimaryExp
    :   mathLiteralExp
    |   mathFunctionApplicationExp
    |   mathCrossTypeExp
    |   mathSegmentsExp
    |   mathOutfixExp
    |   mathSetExp
    |   mathTupleExp
    |   mathAlternativeExp
    |   mathLambdaExp
    ;

mathLiteralExp
    :   (qualifier=ID COLONCOLON)? bool=BOOL        #mathBooleanExp
    |   (qualifier=ID COLONCOLON)? num=INT         #mathIntegerExp
    ;

mathFunctionApplicationExp
    :   (AT)? name=ID (LPAREN mathExp (COMMA mathExp)* RPAREN)+ #mathFunctionExp
    |   (AT)? (qualifier=ID COLONCOLON)? name=ID #mathVariableExp
    ;

mathCrossTypeExp
    :   CART_PROD (mathVariableDeclGroup SEMI)+ END
    ;

mathOutfixExp
    :   lop=LT mathExp rop=GT
    |   lop=BAR mathExp rop=BAR
    |   lop=DBL_BAR mathExp rop=DBL_BAR
    ;

mathSetExp
    :   LBRACE mathVariableDecl BAR mathAssertionExp RBRACE  #mathSetBuilderExp//Todo
    |   LBRACE (mathExp (COMMA mathExp)*)? RBRACE         #mathSetCollectionExp
    ;

mathLambdaExp
    :   LAMBDA LPAREN mathVariableDeclGroup
        (COMMA mathVariableDeclGroup)* RPAREN DOT LPAREN mathExp RPAREN
    ;

mathAlternativeExp
    :   DBL_LBRACE (mathAlternativeItemExp)+ DBL_RBRACE
    ;

mathAlternativeItemExp
    :   result=mathExp (IF condition=mathExp SEMI | OTHERWISE SEMI)
    ;

mathTupleExp
    :   LPAREN mathExp (COMMA mathExp)+ RPAREN
    ;

mathSegmentsExp
    :   mathFunctionApplicationExp (DOT mathFunctionApplicationExp)+
    ;
