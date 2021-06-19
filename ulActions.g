/*
An ANTLR 3.0 action grammar for the Unnamed Language

Author: Helen Dun
VNum: V00912482
*/

grammar ulActions;

@header 
{
    import java.util.Vector;
    import ast.*;
    import static ast.Type.*;
    import static ast.Operator.*;
}

/* Parser */

program returns [Program p]
    : f1=function 
        {p = new Program(f1.getLine(), f1.getCharPositionInLine(), f1.getTokenIndex()); p.addFunction(f1);} 
    (f2=function {p.addFunction(f2);})* EOF
    ;

function returns [Function f3]
    : fd1=functionDecl fb1=functionBody 
        {f3 = new Function(fd1.getLine(), fd1.getCharPositionInLine(), fd1.getTokenIndex(), fd1, fb1);}
    ;

functionDecl returns [FunctionDeclaration fd2]
    : ct1=compoundType i1=id PAREN_LEFT PAREN_RIGHT
        {fd2 = new FunctionDeclaration(ct1.getLine(), ct1.getCharPositionInLine(), ct1.getTokenIndex(), ct1, i1, null);}
    | ct6=compoundType i17=id PAREN_LEFT fp1=formalParameters PAREN_RIGHT
        {fd2 = new FunctionDeclaration(ct6.getLine(), ct6.getCharPositionInLine(), ct6.getTokenIndex(), ct6, i17, fp1);}
    ;

formalParameters returns [Vector<Variable> vs1]
@init
{
    vs1 = new Vector<Variable>();
}
    : ct2=compoundType i2=id 
    {
        Variable v4 = new Variable(ct2.getLine(), ct2.getCharPositionInLine(), ct2.getTokenIndex(), ct2, i2);
        vs1.add(v4);
    }
        (v1=moreFormals {vs1.add(v1);})*
    ;

moreFormals returns [Variable v2]
    : COMMA ct3=compoundType i3=id
        {v2 = new Variable(ct3.getLine(), ct3.getCharPositionInLine(), ct3.getTokenIndex(), ct3, i3);}
    ;

// get the variable declarations and statements of the function (body)
functionBody returns [FunctionBody fb2]
@init
{
    Vector<Variable> vs2 = new Vector<Variable>();
    Vector<Statement> ss1 = new Vector<Statement>();
}
@after
{
    fb2 = new FunctionBody(cl1.getLine(), cl1.getCharPositionInLine(), cl1.getTokenIndex(), vs2, ss1);
}
    : cl1=CURLY_LEFT (vd1=varDecl {vs2.add(vd1);})* (s1=statement {ss1.add(s1);})* CURLY_RIGHT
    ;

varDecl returns [Variable v3]
    : ct4=compoundType i4=id SEMICOLON
        {v3 = new Variable(ct4.getLine(), ct4.getCharPositionInLine(), ct4.getTokenIndex(), ct4, i4);}
    ;

compoundType returns [CompoundType ct5]
    : t1=type
        {ct5 = new CompoundType(t1.getLine(), t1.getCharPositionInLine(), t1.getTokenIndex(), t1, -1);}
    | t2=type BRACKET_LEFT i5=INTEGERCONSTANT BRACKET_RIGHT
        {ct5 = new CompoundType(t2.getLine(), t2.getCharPositionInLine(), t2.getTokenIndex(), t2, Integer.parseInt(i5.getText()));}
    ;

type returns [TypeNode t3]
    : x1=INT {t3 = new TypeNode(x1.getLine(), x1.getCharPositionInLine(), x1.getTokenIndex(), Type_Int);}
    | x2=FLOAT {t3 = new TypeNode(x2.getLine(), x2.getCharPositionInLine(), x2.getTokenIndex(), Type_Float);}
    | x3=CHAR {t3 = new TypeNode(x3.getLine(), x3.getCharPositionInLine(), x3.getTokenIndex(), Type_Char);}
    | x4=STRING {t3 = new TypeNode(x4.getLine(), x4.getCharPositionInLine(), x4.getTokenIndex(), Type_String);}
    | x5=BOOLEAN {t3 = new TypeNode(x5.getLine(), x5.getCharPositionInLine(), x5.getTokenIndex(), Type_Boolean);}
    | x6=VOID {t3 = new TypeNode(x6.getLine(), x6.getCharPositionInLine(), x6.getTokenIndex(), Type_Void);}
    ;

statement returns [Statement s2] options {backtrack=true;} 
    : i7=IF PAREN_LEFT e2=expr PAREN_RIGHT b2=block ELSE b3=block
        {s2 = new StatementIfElse(i7.getLine(), i7.getCharPositionInLine(), i7.getTokenIndex(), e2, b2, b3);}
    | i6=IF PAREN_LEFT e1=expr PAREN_RIGHT b1=block
        {s2 = new StatementIfElse(i6.getLine(), i6.getCharPositionInLine(), i6.getTokenIndex(), e1, b1, null);}
    | w1=WHILE PAREN_LEFT e3=expr PAREN_RIGHT ss2=block
        {s2 = new StatementWhile(w1.getLine(), w1.getCharPositionInLine(), w1.getTokenIndex(), e3, ss2);}

    | semi=SEMICOLON
        {s2 = new StatementEmpty(semi.getLine(), semi.getCharPositionInLine(), semi.getTokenIndex());}
    | e4=expr SEMICOLON
        {s2 = new StatementExpression(e4.getLine(), e4.getCharPositionInLine(), e4.getTokenIndex(), e4);}

    | p1=PRINT e5=expr SEMICOLON
        {s2 = new StatementPrint(p1.getLine(), p1.getCharPositionInLine(), p1.getTokenIndex(), e5, false);}
    | p2=PRINTLN e6=expr SEMICOLON
        {s2 = new StatementPrint(p2.getLine(), p2.getCharPositionInLine(), p2.getTokenIndex(), e6, true);}

    | r1=RETURN SEMICOLON
        {s2 = new StatementReturn(r1.getLine(), r1.getCharPositionInLine(), r1.getTokenIndex(), null);}
    | r2=RETURN e7=expr SEMICOLON
        {s2 = new StatementReturn(r2.getLine(), r2.getCharPositionInLine(), r2.getTokenIndex(), e7);}

    | i8=id ASSIGN e8=expr SEMICOLON
        {s2 = new StatementAssignment(i8.getLine(), i8.getCharPositionInLine(), i8.getTokenIndex(), i8, null, e8);}
    | i9=id BRACKET_LEFT e9=expr BRACKET_RIGHT ASSIGN e10=expr SEMICOLON
        {s2 = new StatementAssignment(i9.getLine(), i9.getCharPositionInLine(), i9.getTokenIndex(), i9, e9, e10);}
    ;

block returns [Block b4]
    : cl2=CURLY_LEFT 
        {
            b4 = new Block(cl2.getLine(), cl2.getCharPositionInLine(), cl2.getTokenIndex());
        } 
    (s=statement {b4.add_statement(s);})* CURLY_RIGHT
    ;

expr returns [Expression e11]
    : e12=exprAddSub MULTIPLY e13=expr
        {e11 = new ExpressionOperation(e12.getLine(), e12.getCharPositionInLine(), e12.getTokenIndex(), Operator_Multiply, e12, e13);}
    | e14=exprAddSub
        {e11 = e14;}
    ;

exprAddSub returns [Expression e22]
    : e12=exprLessThan ADDITION e13=exprAddSub
        {e22 = new ExpressionOperation(e12.getLine(), e12.getCharPositionInLine(), e12.getTokenIndex(), Operator_Addition, e12, e13);}
    : e12=exprLessThan SUBTRACTION e13=exprAddSub
        {e22 = new ExpressionOperation(e12.getLine(), e12.getCharPositionInLine(), e12.getTokenIndex(), Operator_Subtraction, e12, e13);}
    | e23=exprLessThan
        {e22 = e23;}

exprLessThan return [Expression e24]
    : e12=exprEqual LESS_THAN e13=exprLessThan
        {e24 = new ExpressionOperation(e12.getLine(), e12.getCharPositionInLine(), e12.getTokenIndex(), Operator_Less_Than, e12, e13);}
    | e25=exprEqual
        {e24 = e25;}

exprEqual return [Expression e26]
    : e12=exprAtom EQUALS e13=exprEqual
        {e26 = new ExpressionOperation(e12.getLine(), e12.getCharPositionInLine(), e12.getTokenIndex(), Operator_Equals, e12, e13);}
    | e27=expexprAtomr4
        {e26 = e27;}

exprAtom returns [Expression e15]
    : l1=literal
        {e15 = l1;}
    | PAREN_LEFT e16=expr PAREN_RIGHT
        {e15 = e16; e15.increment_num_parentheses();}
    | i10=id
        {e15 = new ExpressionIdentifier(i10.getLine(), i10.getCharPositionInLine(), i10.getTokenIndex(), i10, null);}
    | i11=id BRACKET_LEFT e17=expr BRACKET_RIGHT
        {e15 = new ExpressionIdentifier(i11.getLine(), i11.getCharPositionInLine(), i11.getTokenIndex(), i11, e17);}
    | i12=id PAREN_LEFT PAREN_RIGHT
        {e15 = new ExpressionFunction(i12.getLine(), i12.getCharPositionInLine(), i12.getTokenIndex(), i12, null);}
    | i13=id PAREN_LEFT es1=exprList PAREN_RIGHT
        {e15 = new ExpressionFunction(i13.getLine(), i13.getCharPositionInLine(), i13.getTokenIndex(), i13, es1);}
    ;

literal returns [Expression l2]
    : s3=STRINGCONSTANT
        {l2 = new LiteralString(s3.getLine(), s3.getCharPositionInLine(), s3.getTokenIndex(), s3.getText());}
    | i14=INTEGERCONSTANT
        {l2 = new LiteralInteger(i14.getLine(), i14.getCharPositionInLine(), i14.getTokenIndex(), Integer.parseInt(i14.getText()));}
    | f4=FLOATCONSTANT
        {l2 = new LiteralFloat(f4.getLine(), f4.getCharPositionInLine(), f4.getTokenIndex(), Float.parseFloat(f4.getText()));}
    | c1=CHARACTERCONSTANT 
        {l2 = new LiteralCharacter(c1.getLine(), c1.getCharPositionInLine(), c1.getTokenIndex(), c1.getText().charAt(1));}
    | b5=TRUE 
        {l2 = new LiteralBoolean(b5.getLine(), b5.getCharPositionInLine(), b5.getTokenIndex(), true);}
    | b6=FALSE
        {l2 = new LiteralBoolean(b6.getLine(), b6.getCharPositionInLine(), b6.getTokenIndex(), false);}
    ;

exprList returns [Vector<Expression> es2]
@init
{
    es2 = new Vector<Expression>();
}
    : e18=expr {es2.add(e18);} 
        (e19=exprMore 
            {es2.add(e19);}
        )*
    ;

exprMore returns [Expression e20]
    : COMMA e21=expr {e20 = e21;}
    ;

id returns [Identifier i15]
    : i16=ID 
        {i15 = new Identifier(i16.getLine(), i16.getCharPositionInLine(), i16.getTokenIndex(), i16.getText());} 
    ;


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

ID 
    : (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)* 
    ;

WS      
    : (SPACE | TAB | NEWLINE)+ { $channel = HIDDEN; } 
    ;

COMMENT 
    : '//' ~NEWLINE* (NEWLINE | EOF) { $channel = HIDDEN; } 
    ;

EQUALS : '==' ;
LESS_THAN : '<' ;
ADDITION : '+' ;
SUBTRACTION : '-' ;
MULTIPLY : '*' ;

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