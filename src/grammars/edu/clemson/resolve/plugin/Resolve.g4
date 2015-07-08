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
        )*
    ;

// enhancement module

enhancementModule
    :   ENHANCEMENT name=ID (specModuleParameterList)?
        FOR concept=ID SEMI
        (usesList)?
        (requiresClause)?
        (enhancementBlock)
        END closename=ID SEMI
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
        FOR concept=ID SEMI
        (usesList)?
        (requiresClause)?
        (implBlock)
        END closename=ID SEMI
    ;

enhancementImplModule
   :   IMPLEMENTATION name=ID (specModuleParameterList)?
       FOR enhancement=ID OF concept=ID SEMI
       (usesList)?
       (requiresClause)?
       (implBlock)
       END closename=ID SEMI
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
        (usesList)?
        (requiresClause)?
        (facilityBlock)
        END closename=ID SEMI EOF
    ;

facilityBlock
    :   ( operationProcedureDecl
        | facilityDecl
        | typeRepresentationDecl
        )*
    ;

precisModule
    :   PRECIS name=ID SEMI
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

// uses, imports

usesList
    :   USES ID (COMMA ID)* SEMI
    ;

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

stmtBlock
    :   stmt+
    ;

stmt
    :   assignStmt
    |   swapStmt
    |   callStmt
    |   whileStmt
    ;

assignStmt
    :   left=progExp ASSIGN right=progExp SEMI
    ;

swapStmt
    :   left=progExp SWAP right=progExp SEMI
    ;

callStmt
    :   progParamExp SEMI
    ;

whileStmt
    :   WHILE progExp DO
        (stmt)*
        END SEMI
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
        (variableDeclGroup)* (stmtBlock)?
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

mathDefinitionParameter
    :   mathVariableDeclGroup
    |   ID
    ;

mathCategoricalDefinitionDecl
    :   CATEGORICAL DEFINITION FOR
        mathDefinitionSig (COMMA mathDefinitionSig)+
        IS mathAssertionExp SEMI
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

mathSymbol
    :   (PLUS|MINUS|CUTMINUS|DIVIDE|CAT|MULT|BOOL|INT|LTE|LT|GT|GTE)
    |   BAR TRIPLEDOT BAR
    |   LT TRIPLEDOT GT
    |   ID
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
        BY impl=ID (implArgs=moduleArgumentList)? SEMI
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
    :   (recursive=RECURSIVE)? OPERATION
        name=ID operationParameterList (COLON type)? SEMI
        (requiresClause)?
        (ensuresClause)?
        PROCEDURE
        (variableDeclGroup)*
        (stmtBlock)?
        END closename=ID SEMI
    ;

procedureDecl
    :   (recursive=RECURSIVE)? PROCEDURE name=ID operationParameterList
        (COLON type)? SEMI
        (variableDeclGroup)*
        (stmtBlock)?
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

// program expressions

progExp
    :   op=MINUS progExp                        #progInfixExp
    |   progExp op=(MULT|DIVIDE) progExp        #progInfixExp
    |   progExp op=(PLUS|MINUS) progExp         #progInfixExp
    |   progExp op=(LTE|GTE|LT|GT) progExp      #progInfixExp
    |   progExp op=(EQUALS|NEQUALS) progExp     #progInfixExp
    |   LPAREN progExp RPAREN                   #progNestedExp
    |   progPrimary                             #progPrimaryExp
    ;

progPrimary
    :   progLiteralExp
    |   progNamedExp
    |   progParamExp
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
    :   INT      #progIntegerExp
    ;
