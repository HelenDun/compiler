/*
An ANTLR 3.0 non-action grammar for the Unnamed Language

Author: Helen Dun
VNum: V00912482
*/
grammar ulNoActions;

/* Parser */

program
    : function+ EOF
    ;

function
    : functionDecl functionBody
    ;

functionDecl
    : compoundType id PAREN_LEFT formalParameters? PAREN_RIGHT
    ;

formalParameters
    : compoundType id moreFormals*
    ;

moreFormals
    : COMMA compoundType id
    ;

functionBody
    : CURLY_LEFT varDecl* statement* CURLY_RIGHT
    ;

varDecl
    : compoundType id SEMICOLON
    ;

compoundType
    : type (BRACKET_LEFT INTEGERCONSTANT BRACKET_RIGHT)?
    ;

type
    : INT 
    | FLOAT 
    | CHAR 
    | STRING 
    | BOOLEAN 
    | VOID
    ;

statement options {backtrack=true;}
    : IF PAREN_LEFT expr PAREN_RIGHT block (ELSE block)?
    | WHILE PAREN_LEFT expr PAREN_RIGHT block
    | SEMICOLON
    | PRINT expr SEMICOLON
    | PRINTLN expr SEMICOLON
    | RETURN expr? SEMICOLON
    | id (BRACKET_LEFT expr BRACKET_RIGHT)? ASSIGN expr SEMICOLON
    ;

block
    : CURLY_LEFT statement* CURLY_RIGHT
    ;

expr options {backtrack=true;}
    : expr_lhs op expr
    | expr_lhs
    ;

expr_lhs
    : literal
    | PAREN_LEFT expr PAREN_RIGHT
    | id (BRACKET_LEFT expr BRACKET_RIGHT | PAREN_LEFT exprList? PAREN_RIGHT)?
    ;

literal
    : STRINGCONSTANT 
    | INTEGERCONSTANT 
    | FLOATCONSTANT 
    | CHARACTERCONSTANT 
    | TRUE 
    | FALSE
    ;

exprList
    : expr exprMore*
    ;

exprMore
    : COMMA expr
    ;

id : ID ;
op : OP ;


/* Lexer */

INT : 'int' ;
FLOAT : 'float' ;
CHAR : 'char' ;
STRING : 'string' ;
BOOLEAN : 'boolean' ;
VOID : 'void' ;

TRUE : 'true' ;
FALSE : 'false' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
PRINT : 'print' ;
PRINTLN : 'println' ;
RETURN : 'return' ;

ASSIGN : '=' ;
SEMICOLON : ';' ;
COMMA : ',' ;

PAREN_LEFT : '(' ;
PAREN_RIGHT : ')' ;
BRACKET_LEFT : '[' ;
BRACKET_RIGHT : ']' ;
CURLY_LEFT : '{' ;
CURLY_RIGHT : '}' ;

INTEGERCONSTANT
    : ZERO 
    | NONZERO DIGIT*
    ;

STRINGCONSTANT
    : QUOTE_DOUBLE CHARACTER+ QUOTE_DOUBLE 
    ;

CHARACTERCONSTANT 
    : QUOTE_SINGLE CHARACTER QUOTE_SINGLE 
    ;

FLOATCONSTANT
    : INTEGERCONSTANT PERIOD INTEGERCONSTANT 
    ;

OP 
    : MULTIPLY
    | ADDITION 
    | SUBTRACTION 
    | LESS_THAN
    | EQUALS
    ;

ID 
    : (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)* 
    ;

WS      
    : (SPACE | TAB | NEWLINE)+ { $channel = HIDDEN; } 
    ;

COMMENT 
    : '//' ~NEWLINE* (NEWLINE | EOF) { $channel = HIDDEN; } 
    ;

fragment EQUALS : '==' ;
fragment LESS_THAN : '<' ;
fragment ADDITION : '+' ;
fragment SUBTRACTION : '-' ;
fragment MULTIPLY : '*' ;

fragment SPACE : ' ' ;
fragment TAB : '\t' ;
fragment NEWLINE : '\n' | '\r' ;

fragment ZERO : '0' ;
fragment NONZERO : '1'..'9' ;
fragment DIGIT : ZERO | NONZERO ;

fragment QUOTE_DOUBLE : '"' ;
fragment QUOTE_SINGLE : '\'' ;
fragment UNDERSCORE : '_' ;
fragment COLON : ':' ;
fragment PERIOD : '.' ;
fragment EXCLAMATION : '!' ;
fragment LETTER : 'a'..'z'| 'A'..'Z' ;
fragment CHARACTER 
    : LETTER 
    | DIGIT 
    | COMMA
    | PERIOD
    | UNDERSCORE
    | CURLY_LEFT
    | CURLY_RIGHT
    | SPACE
    | EXCLAMATION
    | COLON
    ;