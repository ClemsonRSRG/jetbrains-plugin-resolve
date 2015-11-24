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
 * documentation and/or other nterials provided with the distribution.
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

moduleDecl
    :   precisModuleDecl
    |   precisExtensionModuleDecl
    ;

precisModuleDecl
    :   PRECIS name=ID SEMI
        (usesList)?
        precisBlock
        END closename=ID SEMI EOF
    ;

precisExtensionModuleDecl
    :   EXTENSION name=ID FOR precis=ID SEMI
        precisBlock
        END closename=ID SEMI
    ;

precisBlock
    :   ( mathStandardDefinitionDecl
        | mathCategoricalDefinitionDecl
        | mathInductiveDefinitionDecl
        | mathTheoremDecl
        )*
    ;

// uses, imports

usesList
    :   USES usesItem (COMMA usesItem)* SEMI
    ;

usesItem
    :   ID (withExtensions)?
    ;

withExtensions
    :   WITH LPAREN ID (COMMA ID)* RPAREN
    ;

// math constructs

mathTheoremDecl
    :   (COROLLARY|THEOREM) name=ID COLON mathAssertionExp SEMI
    ;

mathDefinitionSig
    :   mathPrefixDefinitionSig
    |   mathInfixDefinitionSig
    |   mathOutfixDefinitionSig
    ;

//TODO: ID for now...
mathPrefixDefinitionSig
    :   (ID|mathSymbolName) (LPAREN
            mathVariableDeclGroup (COMMA mathVariableDeclGroup)* RPAREN)?
            COLON mathTypeExp
    ;

mathInfixDefinitionSig
    :   LPAREN mathVariableDecl RPAREN (ID|mathSymbolName)
        LPAREN mathVariableDecl RPAREN COLON mathTypeExp
    ;

mathOutfixDefinitionSig
    :   leftSym=mathSymbolName LPAREN mathVariableDecl RPAREN
        rightSym=mathSymbolName COLON mathTypeExp
    ;

mathSymbolName
    :   (PLUS|MINUS|TRIPLEDOT|DIVIDE|LDIVIDE|BAR|DBL_BAR|LT|GT|CAT|MULT|GTE|LTE|INT|NOT)
    |   BAR TRIPLEDOT BAR
    |   LT TRIPLEDOT GT
    |   DBL_BAR TRIPLEDOT DBL_BAR
    |   LDIVIDE TRIPLEDOT DIVIDE
    ;

mathCategoricalDefinitionDecl
    :   CATEGORICAL DEFINITION FOR
        mathPrefixDefinitionSig (COMMA mathPrefixDefinitionSig)+
        IS mathAssertionExp SEMI
    ;

mathDefinesDefinitionDecl
    :   DEFINES ID (COMMA ID)* COLON mathTypeExp SEMI
    ;

mathStandardDefinitionDecl
    :   (IMPLICIT)? DEFINITION mathDefinitionSig
        (IS mathAssertionExp)? SEMI
    ;

mathInductiveDefinitionDecl
    :   INDUCTIVE DEFINITION mathDefinitionSig IS
        BASE_CASE mathAssertionExp SEMI
        INDUCTIVE_CASE mathAssertionExp SEMI
    ;

mathVariableDeclGroup
    :   mathVarDef (COMMA mathVarDef)* COLON mathTypeExp
    ;

mathVariableDecl
    :   mathVarDef COLON mathTypeExp
    ;

mathVarDef : ID ;

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
    :   q=(FORALL|EXISTS) mathVariableDeclGroup ',' mathAssertionExp
    ;

mathExp
    :   functionExp=mathExp LPAREN mathExp (COMMA mathExp)* RPAREN  #mathPrefixApplyExp
    |   mathExp op=(MULT|DIVIDE|TILDE) mathExp                      #mathInfixApplyExp
    |   mathExp op=(PLUS|MINUS) mathExp                             #mathInfixApplyExp
    |   mathExp op=(RANGE|RARROW) mathExp                           #mathInfixApplyExp
    |   mathExp op=(CAT|UNION|INTERSECT) mathExp                    #mathInfixApplyExp
    |   mathExp op=(IS_IN|IS_NOT_IN) mathExp                        #mathInfixApplyExp
    |   mathExp op=(LTE|GTE|GT|LT) mathExp                          #mathInfixApplyExp
    |   mathExp op=(EQUALS|NEQUALS) mathExp                         #mathInfixApplyExp
    |   mathExp op=(IMPLIES|IFF) mathExp                            #mathInfixApplyExp
    |   mathExp op=(AND|OR) mathExp                                 #mathInfixApplyExp
    |   mathExp op=COLON mathTypeExp                                #mathTypeAssertionExp
    |   LPAREN mathAssertionExp RPAREN                              #mathNestedExp
    |   mathPrimaryExp                                              #mathPrimeExp
    ;

mathPrimaryExp
    :   mathLiteralExp
    |   mathCrossTypeExp
    |   mathSymbolExp
    |   mathOutfixApplyExp
    |   mathSetComprehensionExp
    |   mathSetExp
    |   mathLambdaExp
    |   mathAlternativeExp
    |   mathTupleExp
    |   mathSegmentsExp
    ;

mathLiteralExp
    :   (qualifier=ID COLONCOLON)? (TRUE|FALSE)     #mathBooleanLiteralExp
    |   (qualifier=ID COLONCOLON)? num=INT          #mathIntegerLiteralExp
    ;

mathCrossTypeExp
    :   CART_PROD (mathVariableDeclGroup SEMI)+ END
    ;

mathSymbolExp
    :   (incoming=AT)? (qualifier=ID COLONCOLON)? (ID|mathSymbolName)
    ;

mathOutfixApplyExp
    :   lop=LT mathExp rop=GT
    |   lop=BAR mathExp rop=BAR
    |   lop=DBL_BAR mathExp rop=DBL_BAR
    ;

mathSetComprehensionExp
    :   LBRACE mathVariableDecl BAR mathAssertionExp RBRACE
    ;

mathSetExp
    :    LBRACE (mathExp (COMMA mathExp)*)? RBRACE
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

//Segments can end in an application but they can't contain one in the middle ..
//hopefully :) ...
mathSegmentsExp
    :   mathSymbolExp (DOT mathSymbolExp)+ (LPAREN mathExp (COMMA mathExp)* RPAREN)?
    ;