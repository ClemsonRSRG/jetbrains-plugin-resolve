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
    |   precisExtensionModule
    |   conceptModule
    |   conceptImplModule
    |   facilityModule
    |   enhancementImplModule
    |   enhancementModule
    ;

conceptModule
    :   CONCEPT name=ID (LT genericType (COMMA genericType)* GT)?
        (specModuleParameterList)? SEMI
        (dependentTermOptions)?
        (usesList)?
        (requiresClause)?
        (conceptBlock)
        END closename=ID SEMI EOF
    ;

conceptBlock
    :   ( typeModelDecl
        | operationDecl
        | mathDefinitionDecl
        | mathDefinesDefinitionDecl
        | constraintClause
        )*
    ;

// enhancement module

enhancementModule
    :   EXTENSION name=ID (specModuleParameterList)?
        FOR concept=ID SEMI
        (dependentTermOptions)?
        (usesList)?
        (requiresClause)?
        (enhancementBlock)
        END closename=ID SEMI EOF
    ;

enhancementBlock
    :   ( operationDecl
        | typeModelDecl
        | mathDefinitionDecl
        )*
    ;

// implementation modules

conceptImplModule
    :   IMPLEMENTATION name=ID (implModuleParameterList)?
        (dependentTermOptions)?
        FOR concept=ID SEMI
        (usesList)?
        (requiresClause)?
        (implBlock)
        END closename=ID SEMI EOF
    ;

enhancementImplModule
   :   IMPLEMENTATION name=ID (implModuleParameterList)?
       (dependentTermOptions)?
       FOR enhancement=ID OF concept=ID SEMI
       (usesList)?
       (requiresClause)?
       (implBlock)
       END closename=ID SEMI EOF
   ;

implBlock
    :   ( typeRepresentationDecl
        | operationProcedureDecl
        | procedureDecl
        | facilityDecl
        )*
    ;

// facility modules

facilityModule
    :   FACILITY name=ID SEMI
        (dependentTermOptions)?
        (usesList)?
        (requiresClause)?
        (facilityBlock)
        END closename=ID SEMI EOF
    ;

facilityBlock
    :   ( mathDefinitionDecl
        | operationProcedureDecl
        | facilityDecl
        | typeRepresentationDecl
        )*
    ;

precisModule
    :   PRECIS name=ID SEMI
        (dependentTermOptions)?
        (usesList)?
        precisBlock
        END closename=ID SEMI EOF
    ;

precisBlock
    :   ( mathDefinitionDecl
        | mathCategoricalDefinitionDecl
        | mathInductiveDefinitionDecl
        | mathTheoremDecl
        )*
    ;

// precis extension

precisExtensionModule
    :   PRECIS EXTENSION name=ID FOR precis=ID
        (EXTENDED BY (ID (COMMA ID)*))? SEMI
        (usesList)?
        precisBlock
        END closename=ID SEMI
    ;

// uses, imports

usesList
    :   USES usesItem (COMMA usesItem)* SEMI
    ;

usesItem : ID ;

// temp soln.
dependentTermOptions
    :   AT DEPENDENT LBRACE ID (COMMA ID)* RBRACE
    ;

// parameter and parameter-list related rules

operationParameterList
    :   LPAREN (parameterDeclGroup (SEMI parameterDeclGroup)*)?  RPAREN
    ;

specModuleParameterList
    :   LPAREN specModuleParameterDecl (SEMI specModuleParameterDecl)* RPAREN
    ;

implModuleParameterList
    :   LPAREN implModuleParameterDecl (SEMI implModuleParameterDecl)* RPAREN
    ;

specModuleParameterDecl
    :   parameterDeclGroup
    |   mathDefinitionDecl
    ;

implModuleParameterDecl
    :   parameterDeclGroup
    |   operationDecl
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

variableDeclGroup
    :   VAR ID (COMMA ID)* COLON type SEMI
    ;

// statements

stmt
    :   assignStmt
    |   swapStmt
    |   callStmt
    |   whileStmt
    |   ifStmt
    ;

assignStmt
    :   left=progVarExp ASSIGN right=progExp SEMI
    ;

swapStmt
    :   left=progVarExp SWAP right=progVarExp SEMI
    ;

//semantically restrict things like 1++ (<literal>++/--, etc)
callStmt
    :   progExp SEMI
    ;

whileStmt
    :   WHILE progExp DO
        (stmt)*
        END SEMI
    ;

ifStmt
    :   IF progExp THEN stmt* (elsePart)? END SEMI
    ;

elsePart
    :   ELSE stmt*
    ;

// type and record related rules

type
    :   (qualifier=ID COLONCOLON)? name=ID
    ;

genericType
    :   ID
    ;

record
    :   RECORD (recordVariableDeclGroup)+ END
    ;

recordVariableDeclGroup
    :   ID (COMMA ID)* COLON type SEMI
    ;

typeModelDecl
    :   TYPE FAMILY name=ID IS MODELED BY mathTypeExp SEMI
        EXEMPLAR exemplar=ID SEMI
        (constraintClause)?
        (typeModelInit)?
    ;

typeRepresentationDecl
    :   TYPE name=ID EQUALS (type|record) SEMI
        (conventionClause)?
        (correspondenceClause)?
        (typeImplInit)?
    ;

// type initialization rules

typeModelInit
    :   INITIALIZATION (ensuresClause)?
    ;

typeImplInit
    :   INITIALIZATION (ensuresClause)?
        (variableDeclGroup)* (stmt)*
        END SEMI
    ;

// math constructs

mathTheoremDecl
    :   (COROLLARY|THEOREM) name=ID COLON mathAssertionExp SEMI
    ;

//The '(COMMA ID)?' is reserved for the variable we're inducting over
//in the context of an inductive defn
mathDefinitionSig
    :   name=mathSymbol (LPAREN
            mathDefinitionParameter (COMMA mathDefinitionParameter)* RPAREN)?
            COLON mathTypeExp
    ;

//Todo: Clean this up for god's sake.
mathSymbol
    :   ID (PLUS|MINUS|CUTMINUS|TRIPLEDOT|DIVIDE|LDIVIDE|BAR|DBL_BAR|LT|GT|CAT|MULT|GTE|LTE)?
    |   (PLUS|MINUS|CUTMINUS|TRIPLEDOT|DIVIDE|LDIVIDE|BAR|DBL_BAR|LT|GT|CAT|MULT|GTE|LTE|INT)
    |   BAR TRIPLEDOT BAR
    |   LT TRIPLEDOT GT
    |   DBL_BAR TRIPLEDOT DBL_BAR
    |   LDIVIDE TRIPLEDOT DIVIDE
    ;

mathDefinitionParameter
    :   mathVariableDeclGroup
    |   ID
    ;

mathCategoricalDefinitionDecl
    :   CATEGORICAL DEFINITION FOR
        mathDefinitionSig (COMMA mathDefinitionSig)+
        IS mathAssertionExp SEMI
    ;

mathDefinesDefinitionDecl
    :   DEFINES ID (COMMA ID)* COLON mathTypeExp SEMI
    ;

mathDefinitionDecl
    :   (IMPLICIT)? DEFINITION mathDefinitionSig
        (IS mathAssertionExp)? SEMI
    ;

mathInductiveDefinitionDecl
    :   INDUCTIVE DEFINITION ON mathVariableDecl OF mathDefinitionSig IS
        BASE_CASE mathAssertionExp SEMI
        INDUCTIVE_CASE mathAssertionExp SEMI
    ;

mathVariableDeclGroup
    :   ID (COMMA ID)* COLON mathTypeExp
    ;

mathVariableDecl
    :   ID COLON mathTypeExp
    ;

// facilitydecls, enhancements, etc

facilityDecl
    :   FACILITY name=ID IS spec=ID (LT type (COMMA type)* GT)?
        (specArgs=moduleArgumentList)? (externally=EXTERNALLY)? IMPLEMENTED
        BY impl=ID (implArgs=moduleArgumentList)? (enhancementPairDecl)* SEMI
    ;

enhancementPairDecl
    :   EXTENDED BY spec=ID (LT type (COMMA type)* GT)?
        (specArgs=moduleArgumentList)?
        (externally=EXTERNALLY)? IMPLEMENTED BY impl=ID
        (implArgs=moduleArgumentList)?
    ;

moduleArgumentList
    :   LPAREN moduleArgument (COMMA moduleArgument)* RPAREN
    ;

moduleArgument
    :   progExp
    ;

// functions

operationDecl
    :   OPERATION name=ID operationParameterList (COLON type)? SEMI
        (requiresClause)? (ensuresClause)?
    ;

operationProcedureDecl
    :   OPERATION
        name=ID operationParameterList (COLON type)? SEMI
        (requiresClause)?
        (ensuresClause)?
        (recursive=RECURSIVE)? PROCEDURE
        (variableDeclGroup)*
        (stmt)*
        END closename=ID SEMI
    ;

procedureDecl
    :   (recursive=RECURSIVE)? PROCEDURE name=ID operationParameterList
        (COLON type)? SEMI
        (variableDeclGroup)*
        (stmt)*
        END closename=ID SEMI
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
    :   op=NOT mathExp                                  #mathUnaryExp
    |   mathExp op=(MULT|DIVIDE|TILDE) mathExp          #mathInfixExp
    |   mathExp op=(PLUS|MINUS|CUTMINUS) mathExp        #mathInfixExp
    |   mathExp op=(RANGE|RARROW) mathExp               #mathInfixExp
    |   mathExp op=(CAT|UNION|INTERSECT) mathExp        #mathInfixExp
    |   mathExp op=(IS_IN|IS_NOT_IN) mathExp            #mathInfixExp
    |   mathExp op=(LTE|GTE|GT|LT) mathExp              #mathInfixExp
    |   mathExp mathSymbol mathExp                      #mathCustomInfixExp     //TODO: Be careful with this alt.!
    |   mathExp op=(EQUALS|NEQUALS) mathExp             #mathInfixExp
    |   mathExp op=IMPLIES mathExp                      #mathInfixExp
    |   mathExp op=(AND|OR) mathExp                     #mathInfixExp
    |   mathExp op=COLON mathTypeExp                    #mathTypeAssertionExp
    |   LPAREN mathAssertionExp RPAREN                  #mathNestedExp
    |   mathPrimaryExp                                  #mathPrimeExp
    ;

mathPrimaryExp
    :   mathLiteralExp
    |   mathFunctionApplicationExp
    |   mathFunctionRestrictionExp
    |   mathCrossTypeExp
    |   mathSegmentsExp
    |   mathOutfixExp
    |   mathSetExp
    |   mathTupleExp
    |   mathAlternativeExp
    |   mathLambdaExp
    ;

mathLiteralExp
    :   (qualifier=ID COLONCOLON)? (TRUE|FALSE)     #mathBooleanLiteralExp
    |   (qualifier=ID COLONCOLON)? num=INT          #mathIntegerLiteralExp
    ;

mathFunctionApplicationExp
    :   (AT)? (qualifier=ID COLONCOLON)? name=ID (LPAREN mathExp (COMMA mathExp)* RPAREN)+ #mathFunctionExp
    |   (AT)? (qualifier=ID COLONCOLON)? name=ID #mathVariableExp
    ;

mathFunctionRestrictionExp
    :   restrictionFunctionExp LBRACKET mathExp RBRACKET
    ;

restrictionFunctionExp
    :   mathFunctionApplicationExp
    |   mathSegmentsExp
    ;

mathCrossTypeExp
    :   CART_PROD (mathVariableDeclGroup SEMI)+ END
    ;

mathOutfixExp
    :   lop=LT mathExp rop=GT
    |   lop=BAR mathExp rop=BAR
    |   lop=DBL_BAR mathExp rop=DBL_BAR
    |   lop=LDIVIDE mathExp rop=DIVIDE
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
    :   (AT)? mathFunctionApplicationExp (DOT mathFunctionApplicationExp)+
    ;

// program expressions

//Todo: I think precedence, and the ordering of these alternatives is nearly there -- if not already.
//we could really use some unit tests to perhaps check precendence so that in the future when
//someone comes in and mucks with the grammar, our tests will indicate that precedence is right or wrong.
progExp
    :   progPrimary                                     #progPrimaryExp
    |   LPAREN progExp RPAREN                           #progNestedExp
    |   op=(MINUS|NOT) progExp                          #progUnaryExp
    |   progExp op=(PLUSPLUS|MINUSMINUS)                #progPostfixExp
    |   progExp op=MOD progExp                          #progInfixExp
    |   progExp op=(MULT|DIVIDE|PLUSPLUS) progExp       #progInfixExp
    |   progExp op=(PLUS|MINUS) progExp                 #progInfixExp
    |   progExp op=(LTE|GTE|LT|GT) progExp              #progInfixExp
    |   progExp op=(EQUALS|NEQUALS) progExp             #progInfixExp
    |   progExp op=AND progExp                          #progInfixExp
    |   progExp op=OR progExp                          #progInfixExp
    ;

progPrimary
    :   progLiteralExp
    |   progVarExp
    |   progParamExp
    ;

progVarExp
    :   progNamedExp
    |   progMemberExp
    ;

progParamExp
    :   (qualifier=ID COLONCOLON)? name=ID
        LPAREN (progExp (COMMA progExp)*)? RPAREN
    ;

progNamedExp
    :   (qualifier=ID COLONCOLON)? name=ID
    ;

progMemberExp
    :   (progParamExp|progNamedExp) (DOT ID)+
    ;

progLiteralExp
    :   (TRUE|FALSE)    #progBooleanLiteralExp
    |   INT             #progIntegerLiteralExp
    |   CHAR            #progCharacterLiteralExp
    |   STRING          #progStringLiteralExp
    ;
